import com.beust.jcommander.JCommander;
import mechanics.system.annotation.AnnotationTransformer;
import mechanics.system.credentials.RoleSwitcher;
import mechanics.system.credentials.Users;
import mechanics.system.email.EmailChecker;
import mechanics.system.jar.Args;
import mechanics.system.jar.Unpack;
import org.apache.commons.io.output.TeeOutputStream;
import org.testng.TestNG;
import org.testng.collections.Lists;
import ru.yandex.qatools.allure.report.AllureReportBuilder;
import ru.yandex.qatools.allure.report.AllureReportBuilderException;
import ru.yandex.qatools.allure.utils.AllureResultsUtils;

import java.io.*;
import java.util.List;

/**
 * Created by Alex Storm on 12.05.2017.
 */
public class Main {
    private static FileOutputStream fos = null;

    public static void main(String[] args) {
        Args argv = new Args();
        JCommander.newBuilder()
                .addObject(argv)
                .build()
                .parse(args);
        argv.help();
        argv.print();
        Unpack unpack = new Unpack();
        unpack.unpackOnStart();
        if (!Args.unpackOnly) {
            argv.setStage();
            argv.setRoles();
            long time = System.currentTimeMillis();
            TestNG testng = null;
            Users.initializeCreds();
            if (!Args.noInit) {
                Users.initializeAwsKeys();
            }
            for (int i = 0; i <= RoleSwitcher.getRolesCount() - 1; i++) {
                File path2 = new File("reports/allure_" + Args.stage + "_" + time + "_" + RoleSwitcher.getCurrentRoleName());
                if (!path2.getAbsoluteFile().exists()) {
                    path2.getAbsoluteFile().mkdirs();
                }
                startStream(path2.getAbsolutePath());
                EmailChecker.cleanInbox();//clean inbox on role start
                AllureResultsUtils.setResultsDirectory(path2);
                testng = new TestNG();
                List<String> suites = Lists.newArrayList();
                if (new File("xml/" + Args.file).exists()) {
                    suites.add("xml/" + Args.file);
                } else {
                    System.out.println("ERR: XML File 'xml/" + Args.file + "' not found");
                    System.exit(1);
                }
                testng.setUseDefaultListeners(false);
                testng.setOutputDirectory(path2.getAbsolutePath());
                testng.setTestSuites(suites);
                testng.setVerbose(2);
                testng.setAnnotationTransformer(new AnnotationTransformer());
                testng.run();
                File output2 = new File(path2.getAbsolutePath() + "/allure-report/");
                try {
                    AllureReportBuilder allureReportBuilder = new AllureReportBuilder(output2);
                    allureReportBuilder.unpackFace();
                    allureReportBuilder.processResults(path2);
                } catch (AllureReportBuilderException e) {
                    e.printStackTrace();
                }
                EmailChecker.cleanInbox();//clean inbox on role finish
                stopStream();
                RoleSwitcher.roleFinished();
            }
            //if any test fails - exit code is 1
            if (testng.hasFailure() || testng.hasFailureWithinSuccessPercentage()) {
                System.out.println("testng.hasFailure: " + testng.hasFailure());
                System.out.println("testng.hasFailureWithinSuccessPercentage: " + testng.hasFailureWithinSuccessPercentage());
                System.exit(1);
            }
        }

    }

    private static void startStream(String path) {
        try {
            fos = new FileOutputStream(path + "/testlog.log");
            TeeOutputStream myOut = new TeeOutputStream(System.out, fos);
            PrintStream ps = new PrintStream(myOut);
            System.setOut(ps);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static void stopStream() {
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
