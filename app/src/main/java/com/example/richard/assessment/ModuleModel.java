package com.example.richard.assessment;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModuleModel {
    private int id;
    private String moduleDescription, moduleName, videoId;
    List<String> modDesc, modName, vidId;

    public ModuleModel() {
    }

    public ModuleModel(int id, String moduleName, String moduleDescription, String videoId) {
        this.id = id;
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
        this.videoId = videoId;
    }

    public ArrayList<ModuleModel> getModuleList() {
        ArrayList<ModuleModel> moduleList = new ArrayList<>();
        modDesc = Arrays.asList(Resources.getSystem().getStringArray(R.array.moduleDescriptions));
        modName = Arrays.asList(Resources.getSystem().getStringArray(R.array.moduleTitles));
        vidId = Arrays.asList(Resources.getSystem().getStringArray(R.array.video_IDs));

        for (int i = 0; i < modDesc.size(); i++) {
            moduleList.add(new ModuleModel((i + 1), modDesc.get(i), modName.get(i), vidId.get(i)));
        }
        return moduleList;
    }

    public int getId() {
        return this.id;
    }

    public String getModuleDescription() {
        return this.moduleDescription;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public String getVideoId() {
        return this.videoId;
    }
}

