package com.diabin.latte.app;

import java.util.WeakHashMap;

public class Configurator {
    private static  final WeakHashMap<String,Object> LATTE_CONFIGS = new WeakHashMap<>();

            private Configurator(){
                    LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);//配置完成，但是没有初始化

            }

            public static Configurator getInstance(){
                return Holder.INSTANCE;
            }

            final  WeakHashMap<String,Object> getLatteConfigs(){
                return LATTE_CONFIGS;
            }
            private static class Holder {
                private static final Configurator INSTANCE =  new Configurator();//保证多线程安全
            }

            public final void configure(){
                LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);//配置完成，改为ture
            }

            //配置apihost
            public final Configurator withApiHost(String host){
                LATTE_CONFIGS.put(ConfigType.API_HOST.name(),host);
                return this;
            }

            private void checkConfiguration(){
                final boolean isReady = (boolean)LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
                if (!isReady){
                    throw new RuntimeException("Configuration is not ready,call configure");
                }
            }

            @SuppressWarnings("unchecked")
            final <T> T getConfiguration(Enum<ConfigType> key){
                checkConfiguration();
                return (T) LATTE_CONFIGS.get(key.name());
            }

}
