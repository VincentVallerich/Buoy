package fr.ensisa.gmv.buoys.network;

public class Protocol {

    public static final int BUOYS_UDP_PORT 				= 6666;
    public static final int BUOYS_TCP_PORT				= 7777;

    private static final int REQUEST_START				= 1000;
    private static final int REPLY_START				= 2000;

    private static final int COMMON_START				= 000;
    private static final int BUOY_START					= 100;
    private static final int CONFIG_START				= 200;
    private static final int CLIENT_START				= 300;
    private static final int SERVICE_START				= 400;

	public static final int REPLY_KO					= REPLY_START+COMMON_START+2;
	public static final int REPLY_OK                    = REPLY_START+COMMON_START+3;

	public static final int UDP_STD1					= 0x01;
	public static final int UDP_STD2					= 0x02;
	public static final int UDP_SERVICE					= 0x03;

	public static final int GET_CONFIG_GET_VERSION      = 3000;
	public static final int GET_CONFIG_NEW_VERSION      = 3001;

    public static final int GET_CONFIG_CREATE_BUOY      = 4000;
    public static final int GET_CONFIG_UPDATE_BUOY      = 4001;
    public static final int GET_CONFIG_GET_BUOY         = 4002;
    public static final int GET_CONFIG_GET_BUOYLIST     = 4003;
    public static final int GET_CONFIG_DELETE_BUOY      = 4004;

    public static final int GET_SESSION_BUOY_LAST_TICK  = 5000;
}
