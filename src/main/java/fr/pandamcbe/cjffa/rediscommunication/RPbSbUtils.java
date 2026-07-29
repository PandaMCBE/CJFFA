package fr.pandamcbe.cjffa.rediscommunication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.pandamcbe.cjffa.CJFFA;
import fr.pandamcbe.commons.FFAKitsAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.core.localmemory.MemAccountsManipulation;
import fr.pandamcbe.mgapi.core.rediscache.CallAccountsFromRedis;
import fr.pandamcbe.mgapi.data.management.redis.datas_manipulation.RedisAccountsUtils;
import fr.pandamcbe.mgapi.data.management.redis.datas_manipulation.RedisMessage;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class RPbSbUtils extends RedisAccountsUtils {

    @Override
    public void subChannels() {
        String[] channels = {AllNames.CJFFA.getName(), AllNames.SAVESIGNAL.getName()};
        subCore(channels);
    }
    @Override
    public void handleRedisMessage(String channelName, String message) {
        CJFFA main = CJFFA.getInstance();
        Gson gson = Main.getInstance().gson;
        AllNames channelsName = AllNames.fromName(channelName);
        if (Objects.requireNonNull(channelsName) == AllNames.CJFFA) {
            RedisMessage redisMessage = gson.fromJson(message, RedisMessage.class);
            UUID uuid = redisMessage.getUuid();
            Map<String, JsonObject> accounts = redisMessage.getAccounts();
            for (String accountNames : accounts.keySet()) {
                MemAccountsManipulation manipulation = Main.getInstance().manipulation;
                AllNames allNames2 = AllNames.fromName(CallAccountsFromRedis.ifContainsCategory(accountNames));
                if (Objects.requireNonNull(allNames2) == AllNames.FFAKITSPOJO) {
                    FFAKitsAccount ffaKitsAccount = gson.fromJson(accounts.get(accountNames), FFAKitsAccount.class);
                    manipulation.sendAccountToMemory(main.mainMap, uuid, ffaKitsAccount, AllNames.FFAKITSPOJO.getName());
                }
            }
        }
    }

//    public void sendAccountsToProxy(CJFFA main){
//        Gson gson = Main.getInstance().gson;
//        Map<String, String> mainMapJson = new HashMap<>();
//        Map<String, JsonObject> subMapJson = new HashMap<>();
//        for(UUID uuid : main.mainMap.keySet()){
//            Map<String, Object> subMap = main.mainMap.get(uuid);
//            for (String accountsName : subMap.keySet()) {
//                JsonObject JsonAccounts = gson.toJsonTree(subMap.get(accountsName)).getAsJsonObject();
//                subMapJson.put(accountsName, JsonAccounts);
//            }
//            String uuidString = uuid.toString();
//            mainMapJson.put(uuidString, gson.toJson(subMapJson));
//        }
//
//        Main.getInstance().redisPbSb.publishMessage(AllNames.PROXY.getName(), gson.toJson(new RedisMessage(mainMapJson)));
//    }

}