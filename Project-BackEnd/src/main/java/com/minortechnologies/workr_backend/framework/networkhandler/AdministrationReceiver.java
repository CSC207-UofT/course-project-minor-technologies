package com.minortechnologies.workr_backend.framework.networkhandler;

import com.minortechnologies.workr_backend.controllers.networkhandler.AdminHandler;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;


@RestController
public class AdministrationReceiver {

    @GetMapping("/Admin/SerializeAllData")
    public int serializeAllData(){
        return AdminHandler.serializeAllData();
    }

    @GetMapping("/Admin/GetAllTokens")
    public ArrayList<Map<String, String>> getAllTokens(){
        return AdminHandler.GetAllTokens();
    }

    @GetMapping("/Admin/GetAllAccounts")
    public ArrayList<Map<String, Object>> getAllAccountData(){
        return AdminHandler.GetAllAccountData();
    }

    @GetMapping("/Admin/Shutdown")
    public void shutdown(){
        AdminHandler.Shutdown();
    }

    @GetMapping("/Admin/SuspendSerialization")
    public int suspendSerialization(){
        return AdminHandler.suspendSerialization();
    }

    @GetMapping("/Admin/ResumeSerialization")
    public int resumeSerialization(){
        return AdminHandler.resumeSerialization();
    }
}
