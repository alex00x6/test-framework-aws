package mechanics.system.annotation;


import mechanics.system.credentials.RoleSwitcher;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by Alex Storm on 02.06.2017.
 */
public class AnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        Annotation[] annotations = testMethod.getDeclaredAnnotationsByType(Role.class);

        String currentRole = RoleSwitcher.getCurrentRoleName();

        for (int i = 0; i < annotations.length; i++) {
            Role role = (Role) annotations[i];
            //if role in annotation same as current role...
            if (role.role().equals(currentRole)) {
                if (!role.expectedStatus()) {
                    annotation.setExpectedExceptions(new Class[]{AssertionError.class});
                }
                if (!role.runnable()) {
                    annotation.setEnabled(false);
                }
            }

            //if current role contains smth from annotation roleContains...
            if (currentRole.contains(role.roleContains())) {
                if (!role.expectedStatus()) {
                    annotation.setExpectedExceptions(new Class[]{AssertionError.class});
                }
                if (!role.runnable()) {
                    annotation.setEnabled(false);
                }
            }
        }
    }
}

