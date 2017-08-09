package mechanics.system.readers;

import com.fasterxml.jackson.databind.ObjectMapper;
import mechanics.system.constant.AssembledEquipments;
import mechanics.system.constant.AssembledUrls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alex Storm on 16.05.2017.
 */
public class Variables {
    //read folder content
    //find all *.json
    //return list of paths to all *.json

    private static HashMap<String, String> urls = null;
    private static HashMap<String, String> equips = null;
    //read json file
    //check if json file contains stage which equals stage
    //if yes
    //if true
    private final String pathToFolder = "variables";

    public void findAndAssembleStage(String stage) {
        findStageJson(stage);
        assembleUrls();
        findEquipJson(stage);
        assembleEquips();
    }

    private void assembleEquips() {
        AssembledEquipments assembledEquipments = new AssembledEquipments();
        assembledEquipments.setEquipmentGpv(equips.get("equipmentGpv"));
        assembledEquipments.setEquipmentGpvName(equips.get("equipmentGpvName"));
        assembledEquipments.setEquipmentGpvData(equips.get("equipmentGpvData"));
        assembledEquipments.setEquipmentGpvMultiDatastreamId(equips.get("equipmentGpvMultiDatastreamId"));
        assembledEquipments.setEquipmentVpv(equips.get("equipmentVpv"));
        assembledEquipments.setEquipmentVpvName(equips.get("equipmentVpvName"));
        assembledEquipments.setEquipmentVpvChannel(equips.get("equipmentVpvChannel"));
        assembledEquipments.setEquipmentVpvChartType(equips.get("equipmentVpvChartType"));
        assembledEquipments.setEquipmentVpvData(equips.get("equipmentVpvData"));
        assembledEquipments.setIctDatastream(equips.get("equipmentIctAnyDataStreamId"));
        assembledEquipments.setIctTopic(equips.get("equipmentIctTopic"));
        assembledEquipments.setIctId(equips.get("equipmentIctId"));
        assembledEquipments.setIctFgwSimulator(equips.get("equipmentIctFgwSimulator"));
        assembledEquipments.setEquipmentItcName1(equips.get("equipmentIctName1"));
        assembledEquipments.setEquipmentItcName2(equips.get("equipmentIctName2"));
        assembledEquipments.setEquipmentItcName3(equips.get("equipmentIctName3"));
        assembledEquipments.setEquipmentItcName4(equips.get("equipmentIctName4"));
        assembledEquipments.setEquipmentItcName5(equips.get("equipmentIctName5"));
    }

    private void assembleUrls() {
        AssembledUrls assembledUrls = new AssembledUrls();
        assembledUrls.setApiUrl(urls.get("API_URL"));
        assembledUrls.setApiUrlMin(urls.get("API_URL_MIN"));
        assembledUrls.setIotEndpoint(urls.get("iotEndpoint"));
        assembledUrls.setRedirectClientURI(urls.get("redirectClientURI"));
        assembledUrls.setSkedlerEndpoint(urls.get("skedlerEndpoint"));
        assembledUrls.assemble();
    }

    private boolean findEquipJson(String stage) {
        boolean found = false;
        ArrayList<String> files = readFolderContent(pathToFolder);
        for (int i = 0; i < files.size(); i++) {
            equips = simpleJsonFileToMap(files.get(i));
            if (equips != null && equips.get("equips") != null && equips.get("equips").equals(stage)) {
                found = true;
                System.out.println("Found suitable .json file with equipments for specified stage '" + stage + "' in folder '" + pathToFolder + "'");
                return found;
            } else {
                equips = null;
            }
        }
        if (!found || equips == null) {
            System.out.println("ERR: Can't find suitable .json file with equipments for specified stage '" + stage + "' in folder '" + pathToFolder + "'");
            System.exit(1);
        }
        return found;
    }


    private boolean findStageJson(String stage) {
        boolean found = false;
        ArrayList<String> files = readFolderContent(pathToFolder);
        for (int i = 0; i < files.size(); i++) {
            urls = simpleJsonFileToMap(files.get(i));
            if (urls != null && urls.get("stage") != null && urls.get("stage").equals(stage)) {
                found = true;
                System.out.println("Found suitable .json file with URLs for specified stage '" + stage + "' in folder '" + pathToFolder + "'");
                return found;
            } else {
                urls = null;
            }
        }
        if (!found || urls == null) {
            System.out.println("ERR: Can't find suitable .json file with URLs for specified stage '" + stage + "' in folder '" + pathToFolder + "'");
            System.exit(1);
        }
        return found;
    }


    private void printMap(HashMap<String, String> map) {
        assert map != null;
        for (HashMap.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + " = " + value);
        }
    }

    private ArrayList<String> readFolderContent(String folderPath) {
        if (!new File(folderPath).isDirectory() || !new File(folderPath).exists()) {
            System.out.println("ERR: Folder '" + folderPath + "' doesn't exist");
            System.exit(1);
        }
        ArrayList<String> files = new ArrayList<>();
        File curDir = new File(folderPath);
        File[] filesList = curDir.listFiles();
        assert filesList != null;
        for (File f : filesList) {
            if (f.isFile() && f.getName().contains(".json")) {
                files.add(f.getAbsolutePath());
//                System.out.println(f.getAbsolutePath());
            }
        }
        return files;
    }

    private HashMap<String, String> simpleJsonFileToMap(String filePath) {
        HashMap<String, String> result = null;
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            result = new ObjectMapper().readValue(stream, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
