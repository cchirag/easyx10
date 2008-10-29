package TextX10;

/**
 * This class provides a container which is used to hold an X10 command
 * to interface with the X10Controller class.
 *
 * @author  Jim Duda
 * @version 1.0, 09/27/08
 */
public class X10Command {

    public static final byte X10_UNIT_CODE_1  = (byte)1;
    public static final byte X10_UNIT_CODE_2  = (byte)2;
    public static final byte X10_UNIT_CODE_3  = (byte)3;
    public static final byte X10_UNIT_CODE_4  = (byte)4;
    public static final byte X10_UNIT_CODE_5  = (byte)5;
    public static final byte X10_UNIT_CODE_6  = (byte)6;
    public static final byte X10_UNIT_CODE_7  = (byte)7;
    public static final byte X10_UNIT_CODE_8  = (byte)8;
    public static final byte X10_UNIT_CODE_9  = (byte)9;
    public static final byte X10_UNIT_CODE_10 = (byte)10;
    public static final byte X10_UNIT_CODE_11 = (byte)11;
    public static final byte X10_UNIT_CODE_12 = (byte)12;
    public static final byte X10_UNIT_CODE_13 = (byte)13;
    public static final byte X10_UNIT_CODE_14 = (byte)14;
    public static final byte X10_UNIT_CODE_15 = (byte)15;
    public static final byte X10_UNIT_CODE_16 = (byte)16;

    public static final byte X10_HOUSE_CODE_A = (byte)1;
    public static final byte X10_HOUSE_CODE_B = (byte)2;
    public static final byte X10_HOUSE_CODE_C = (byte)3;
    public static final byte X10_HOUSE_CODE_D = (byte)4;
    public static final byte X10_HOUSE_CODE_E = (byte)5;
    public static final byte X10_HOUSE_CODE_F = (byte)6;
    public static final byte X10_HOUSE_CODE_G = (byte)7;
    public static final byte X10_HOUSE_CODE_H = (byte)8;
    public static final byte X10_HOUSE_CODE_I = (byte)9;
    public static final byte X10_HOUSE_CODE_J = (byte)10;
    public static final byte X10_HOUSE_CODE_K = (byte)11;
    public static final byte X10_HOUSE_CODE_L = (byte)12;
    public static final byte X10_HOUSE_CODE_M = (byte)13;
    public static final byte X10_HOUSE_CODE_N = (byte)14;
    public static final byte X10_HOUSE_CODE_O = (byte)15;
    public static final byte X10_HOUSE_CODE_P = (byte)16;

    public static final byte X10_FUNCTION_ALL_UNITS_OFF = (byte)0;
    public static final byte X10_FUNCTION_ALL_LIGHTS_ON = (byte)1;
    public static final byte X10_FUNCTION_ON = (byte)2;
    public static final byte X10_FUNCTION_OFF = (byte)3;
    public static final byte X10_FUNCTION_DIM = (byte)4;
    public static final byte X10_FUNCTION_BRIGHT = (byte)5;
    public static final byte X10_FUNCTION_ALL_LIGHTS_OFF = (byte)6;
    public static final byte X10_FUNCTION_EXTENDED_CODE = (byte)7;
    public static final byte X10_FUNCTION_HAIL_REQUEST = (byte)8;
    public static final byte X10_FUNCTION_HAIL_ACKNOWLEDGE = (byte)9;
    public static final byte X10_FUNCTION_PRESET_DIM_1 = (byte)10;
    public static final byte X10_FUNCTION_PRESET_DIM_2 = (byte)11;
    public static final byte X10_FUNCTION_EXTENDED_DATA_TRANSFER = (byte)12;
    public static final byte X10_FUNCTION_STATUS_ON = (byte)13;
    public static final byte X10_FUNCTION_STATUS_OFF = (byte)14;
    public static final byte X10_FUNCTION_STATUS_REQUEST = (byte)15;

    private byte     m_houseCode;
    private byte     m_deviceCode;
    private byte     m_functionCode;

    /**
     * Construct a new X10Command Object
     *
     * @param houseCode - House code value between 'A' and 'P' inclusive.
     * @param deviceCode  - Unit code value between 1 and 16 inclusive.
     * @param commmand  - String ON, OFF, DIM, BRIGHT
     * @see
     */
    public X10Command(char houseCode, byte deviceCode, String function) throws IllegalArgumentException {

        // Validate input parameters and throw exception
        switch (Character.toUpperCase(houseCode) ) {
        case 'A': m_houseCode = X10_HOUSE_CODE_A; break;
        case 'B': m_houseCode = X10_HOUSE_CODE_B; break;
        case 'C': m_houseCode = X10_HOUSE_CODE_C; break;
        case 'D': m_houseCode = X10_HOUSE_CODE_D; break;
        case 'E': m_houseCode = X10_HOUSE_CODE_E; break;
        case 'F': m_houseCode = X10_HOUSE_CODE_F; break;
        case 'G': m_houseCode = X10_HOUSE_CODE_G; break;
        case 'H': m_houseCode = X10_HOUSE_CODE_H; break;
        case 'I': m_houseCode = X10_HOUSE_CODE_I; break;
        case 'J': m_houseCode = X10_HOUSE_CODE_J; break;
        case 'K': m_houseCode = X10_HOUSE_CODE_K; break;
        case 'L': m_houseCode = X10_HOUSE_CODE_L; break;
        case 'M': m_houseCode = X10_HOUSE_CODE_M; break;
        case 'N': m_houseCode = X10_HOUSE_CODE_N; break;
        case 'O': m_houseCode = X10_HOUSE_CODE_O; break;
        case 'P': m_houseCode = X10_HOUSE_CODE_P; break;
        default: throw new IllegalArgumentException("Invalid house code: " + houseCode);
        }

        switch(deviceCode) {
        case 1:  m_deviceCode = X10_UNIT_CODE_1; break;
        case 2:  m_deviceCode = X10_UNIT_CODE_2; break;
        case 3:  m_deviceCode = X10_UNIT_CODE_3; break;
        case 4:  m_deviceCode = X10_UNIT_CODE_4; break;
        case 5:  m_deviceCode = X10_UNIT_CODE_5; break;
        case 6:  m_deviceCode = X10_UNIT_CODE_6; break;
        case 7:  m_deviceCode = X10_UNIT_CODE_7; break;
        case 8:  m_deviceCode = X10_UNIT_CODE_8; break;
        case 9:  m_deviceCode = X10_UNIT_CODE_9; break;
        case 10: m_deviceCode = X10_UNIT_CODE_10; break;
        case 11: m_deviceCode = X10_UNIT_CODE_11; break;
        case 12: m_deviceCode = X10_UNIT_CODE_12; break;
        case 13: m_deviceCode = X10_UNIT_CODE_13; break;
        case 14: m_deviceCode = X10_UNIT_CODE_14; break;
        case 15: m_deviceCode = X10_UNIT_CODE_15; break;
        case 16: m_deviceCode = X10_UNIT_CODE_16; break;
        default: throw new IllegalArgumentException("Invalid unit code: " + deviceCode);
        }

        if (function.equalsIgnoreCase ( "ON" ) ) {
          m_functionCode = X10_FUNCTION_ON;
        } else if (function.equalsIgnoreCase ( "OFF" ) ) {
          m_functionCode = X10_FUNCTION_OFF;
        } else if (function.equalsIgnoreCase ( "DIM" ) ) {
          m_functionCode = X10_FUNCTION_DIM;
        } else if (function.equalsIgnoreCase ( "BRIGHT" ) ) {
          m_functionCode = X10_FUNCTION_BRIGHT;
        } else {
          throw new IllegalArgumentException("Invalid function: " + function);
        }
    }

   /**
     * Copy Constructor for X10Command Object
     *
     */
    public X10Command( X10Command cmd) {
        this.m_houseCode = cmd.m_houseCode;
        this.m_deviceCode = cmd.m_deviceCode;
        this.m_functionCode = cmd.m_functionCode;
    }

    public byte HouseCodeToBinary ( byte houseCode ) {
        byte binary = 0;
        switch (houseCode) {
        case X10_HOUSE_CODE_A: binary = 0x6; break;
        case X10_HOUSE_CODE_B: binary = 0xe; break;
        case X10_HOUSE_CODE_C: binary = 0x2; break;
        case X10_HOUSE_CODE_D: binary = 0xa; break;
        case X10_HOUSE_CODE_E: binary = 0x1; break;
        case X10_HOUSE_CODE_F: binary = 0x9; break;
        case X10_HOUSE_CODE_G: binary = 0x5; break;
        case X10_HOUSE_CODE_H: binary = 0xd; break;
        case X10_HOUSE_CODE_I: binary = 0x7; break;
        case X10_HOUSE_CODE_J: binary = 0xf; break;
        case X10_HOUSE_CODE_K: binary = 0x3; break;
        case X10_HOUSE_CODE_L: binary = 0xb; break;
        case X10_HOUSE_CODE_M: binary = 0x0; break;
        case X10_HOUSE_CODE_N: binary = 0x8; break;
        case X10_HOUSE_CODE_O: binary = 0x4; break;
        case X10_HOUSE_CODE_P: binary = 0xc; break;
        default: assert(false); break;
        }
        return binary;
    }

    public static char BinaryToHouseCode ( byte binary ) {
        char houseCode = 'A';
        switch (binary & 0xf) {
        case 0x6: houseCode = 'A'; break;
        case 0xe: houseCode = 'B'; break;
        case 0x2: houseCode = 'C'; break;
        case 0xa: houseCode = 'D'; break;
        case 0x1: houseCode = 'E'; break;
        case 0x9: houseCode = 'F'; break;
        case 0x5: houseCode = 'G'; break;
        case 0xd: houseCode = 'H'; break;
        case 0x7: houseCode = 'I'; break;
        case 0xf: houseCode = 'J'; break;
        case 0x3: houseCode = 'K'; break;
        case 0xb: houseCode = 'L'; break;
        case 0x0: houseCode = 'M'; break;
        case 0x8: houseCode = 'N'; break;
        case 0x4: houseCode = 'O'; break;
        case 0xc: houseCode = 'P'; break;
        default: assert(false); break;
        }
        return houseCode;
    }

    public byte DeviceCodeToBinary ( byte houseCode ) {
        byte binary = 0;
        switch (houseCode & 0xf) {
        case X10_UNIT_CODE_1:  binary = 0x6; break;
        case X10_UNIT_CODE_2:  binary = 0xe; break;
        case X10_UNIT_CODE_3:  binary = 0x2; break;
        case X10_UNIT_CODE_4:  binary = 0xa; break;
        case X10_UNIT_CODE_5:  binary = 0x1; break;
        case X10_UNIT_CODE_6:  binary = 0x9; break;
        case X10_UNIT_CODE_7:  binary = 0x5; break;
        case X10_UNIT_CODE_8:  binary = 0xd; break;
        case X10_UNIT_CODE_9:  binary = 0x7; break;
        case X10_UNIT_CODE_10: binary = 0xf; break;
        case X10_UNIT_CODE_11: binary = 0x3; break;
        case X10_UNIT_CODE_12: binary = 0xb; break;
        case X10_UNIT_CODE_13: binary = 0x0; break;
        case X10_UNIT_CODE_14: binary = 0x8; break;
        case X10_UNIT_CODE_15: binary = 0x4; break;
        case X10_UNIT_CODE_16: binary = 0xc; break;
        default: assert(false); break;
        }
        return binary;
    }

    public static byte BinaryToDeviceCode ( byte binary ) {
        byte deviceCode = 0;
        switch (binary & 0xf) {
        case 0x6: deviceCode = X10_UNIT_CODE_1; break;
        case 0xe: deviceCode = X10_UNIT_CODE_2; break;
        case 0x2: deviceCode = X10_UNIT_CODE_3; break;
        case 0xa: deviceCode = X10_UNIT_CODE_4; break;
        case 0x1: deviceCode = X10_UNIT_CODE_5; break;
        case 0x9: deviceCode = X10_UNIT_CODE_6; break;
        case 0x5: deviceCode = X10_UNIT_CODE_7; break;
        case 0xd: deviceCode = X10_UNIT_CODE_8; break;
        case 0x7: deviceCode = X10_UNIT_CODE_9; break;
        case 0xf: deviceCode = X10_UNIT_CODE_10; break;
        case 0x3: deviceCode = X10_UNIT_CODE_11; break;
        case 0xb: deviceCode = X10_UNIT_CODE_12; break;
        case 0x0: deviceCode = X10_UNIT_CODE_13; break;
        case 0x8: deviceCode = X10_UNIT_CODE_14; break;
        case 0x4: deviceCode = X10_UNIT_CODE_15; break;
        case 0xc: deviceCode = X10_UNIT_CODE_16; break;
        default: assert(false); break;
        }
        return deviceCode;
    }

    public byte FunctionCodeToBinary ( byte functionCode ) {
        return (m_functionCode);
    }

    public static String BinaryToFunctionCode ( byte binary ) {
        String functionCode = "";
        switch (binary & 0xf) {
        case X10_FUNCTION_ALL_UNITS_OFF:    functionCode = "ALL_UNITS_OFF"; break;
        case X10_FUNCTION_ALL_LIGHTS_ON:    functionCode = "ALL_LIGHTS_ON"; break;
        case X10_FUNCTION_ON:               functionCode = "ON"; break;
        case X10_FUNCTION_OFF:              functionCode = "OFF"; break;
        case X10_FUNCTION_DIM:              functionCode = "DIM"; break;
        case X10_FUNCTION_BRIGHT:           functionCode = "BRIGHT"; break;
        case X10_FUNCTION_ALL_LIGHTS_OFF:   functionCode = "ALL_LIGHTS_OFF"; break;
        case X10_FUNCTION_EXTENDED_CODE:    functionCode = "EXTENDED_CODE"; break;
        case X10_FUNCTION_HAIL_REQUEST:     functionCode = "HAIL_REQUEST"; break;
        case X10_FUNCTION_HAIL_ACKNOWLEDGE: functionCode = "HAIL_ACKNOWLEDGE"; break;
        case X10_FUNCTION_PRESET_DIM_1:     functionCode = "PRESET_DIM_1"; break;
        case X10_FUNCTION_PRESET_DIM_2:     functionCode = "PRESET_DIM_2"; break;
        case X10_FUNCTION_EXTENDED_DATA_TRANSFER: functionCode = "EXTENDED_DATA_TRANSFER"; break;
        case X10_FUNCTION_STATUS_ON:        functionCode = "STATUS_ON"; break;
        case X10_FUNCTION_STATUS_OFF:       functionCode = "STATUS_OFF"; break;
        case X10_FUNCTION_STATUS_REQUEST:   functionCode = "STATUS_REQUEST"; break;
        default: assert(false); break;
        }
        return (functionCode);
    }

    // ACCESS METHODS
    public byte getHouseCode( ) {
        return m_houseCode;
    }

    public byte getHouseCodeBinary( ) {
        return HouseCodeToBinary(m_houseCode);
    }

    public String getHouseCodeString( ) {
        String houseCode = "";
        switch (m_houseCode & 0xf) {
        case X10_HOUSE_CODE_A: houseCode = "A"; break;
        case X10_HOUSE_CODE_B: houseCode = "B"; break;
        case X10_HOUSE_CODE_C: houseCode = "C"; break;
        case X10_HOUSE_CODE_D: houseCode = "D"; break;
        case X10_HOUSE_CODE_E: houseCode = "E"; break;
        case X10_HOUSE_CODE_F: houseCode = "F"; break;
        case X10_HOUSE_CODE_G: houseCode = "G"; break;
        case X10_HOUSE_CODE_H: houseCode = "H"; break;
        case X10_HOUSE_CODE_I: houseCode = "I"; break;
        case X10_HOUSE_CODE_J: houseCode = "J"; break;
        case X10_HOUSE_CODE_K: houseCode = "K"; break;
        case X10_HOUSE_CODE_L: houseCode = "L"; break;
        case X10_HOUSE_CODE_M: houseCode = "M"; break;
        case X10_HOUSE_CODE_N: houseCode = "N"; break;
        case X10_HOUSE_CODE_O: houseCode = "O"; break;
        case X10_HOUSE_CODE_P: houseCode = "P"; break;
        default: assert(false);
        }
        return houseCode;
    }

    public byte getDeviceCode( ) {
        return m_deviceCode;
    }

    public byte getDeviceCodeBinary( ) {
        return DeviceCodeToBinary(m_deviceCode);
    }

    public String getDeviceCodeString( ) {
        String deviceCode = "";
        switch (m_deviceCode & 0xf) {
        case X10_UNIT_CODE_1: deviceCode = "1"; break;
        case X10_UNIT_CODE_2: deviceCode = "2"; break;
        case X10_UNIT_CODE_3: deviceCode = "3"; break;
        case X10_UNIT_CODE_4: deviceCode = "4"; break;
        case X10_UNIT_CODE_5: deviceCode = "5"; break;
        case X10_UNIT_CODE_6: deviceCode = "6"; break;
        case X10_UNIT_CODE_7: deviceCode = "7"; break;
        case X10_UNIT_CODE_8: deviceCode = "8"; break;
        case X10_UNIT_CODE_9: deviceCode = "9"; break;
        case X10_UNIT_CODE_10: deviceCode = "10"; break;
        case X10_UNIT_CODE_11: deviceCode = "11"; break;
        case X10_UNIT_CODE_12: deviceCode = "12"; break;
        case X10_UNIT_CODE_13: deviceCode = "13"; break;
        case X10_UNIT_CODE_14: deviceCode = "14"; break;
        case X10_UNIT_CODE_15: deviceCode = "15"; break;
        case X10_UNIT_CODE_16: deviceCode = "16"; break;
        default: assert(false);
        }
        return deviceCode;
    }

    byte getFunctionCode( ) {
        return m_functionCode;
    }

    byte getFunctionCodeBinary( ) {
        return FunctionCodeToBinary(m_functionCode);
    }

    String getFunctionCodeString( ) {
        String function = "";
        switch (m_functionCode) {
        case X10_FUNCTION_ALL_UNITS_OFF:    function = "ALL_UNITS_OFF"; break;
        case X10_FUNCTION_ALL_LIGHTS_ON:    function = "ALL_LIGHTS_ON"; break;
        case X10_FUNCTION_ON:               function = "ON"; break;
        case X10_FUNCTION_OFF:              function = "OFF"; break;
        case X10_FUNCTION_DIM:              function = "DIM"; break;
        case X10_FUNCTION_BRIGHT:           function = "BRIGHT"; break;
        case X10_FUNCTION_ALL_LIGHTS_OFF:   function = "ALL_LIGHTS_OFF"; break;
        case X10_FUNCTION_EXTENDED_CODE:    function = "EXTENDED_CODE"; break;
        case X10_FUNCTION_HAIL_REQUEST:     function = "HAIL_REQUEST"; break;
        case X10_FUNCTION_HAIL_ACKNOWLEDGE: function = "HAIL_ACKNOWLEDGE"; break;
        case X10_FUNCTION_PRESET_DIM_1:     function = "PRESET_DIM_1"; break;
        case X10_FUNCTION_PRESET_DIM_2:     function = "PRESET_DIM_2"; break;
        case X10_FUNCTION_EXTENDED_DATA_TRANSFER: function = "EXTENDED_DATA_TRANSFER"; break;
        case X10_FUNCTION_STATUS_ON:        function = "STATUS_ON"; break;
        case X10_FUNCTION_STATUS_OFF:       function = "STATUS_OFF"; break;
        case X10_FUNCTION_STATUS_REQUEST:   function = "STATUS_REQUEST"; break;
        default: assert(false);
        }
        return function;
    }

    /**
     *   Produces a human-readable representation of the command.
     *
     *   @return        Something along the lines of "G12_ON" or "E1_OFF" or "C_BRIGHT"
     *                          or "A_DIM".
     */
    public String toString() {
        String s = getHouseCodeString( ) + getDeviceCodeString( ) + "_" + getFunctionCodeString( );
        return s;
    }

}