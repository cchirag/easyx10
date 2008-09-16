package x10;

// Copyright (C) 1999  Moss Prescott
// 
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program (see gpl.html); if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

//package x10;


/*******************************************************************************
 Class encapsulating the bit strings which represent each command known
 to the FireCracker dongle. 
 <P>
 The command-generation was reverse-enginered from the listing in the 
 <A HREF=http://www.x10.com/manuals/cm17a_proto.txt>FireCracker (CM17A) 
 Communications Specification</A> available 
 from <A HREF=http://www.x10.com/>X10</A>.
 		
 @author Moss Prescott (moss@theprescotts.com)
 ******************************************************************************/
public class X10Command {
// STATE
	private String m_bits;
	private String m_description;

// CONSTRUCTOR
	/***************************************************************************
	 Make the A1_ON command, which is commonly used for debugging the hardware.
	 **************************************************************************/
	public static final X10Command 
	makeA1On() {
		return makeOnCommand('A', 1);
	}
	
	/***************************************************************************
	 Make the A1_OFF command, which is commonly used for debugging the hardware.
	 **************************************************************************/
	public static final X10Command 
	makeA1Off() {
		return makeOffCommand('A', 1);
	}

	/***************************************************************************
	 Make a command instructing a particular unit to turn on.
	 
	 @param houseCode	The "housecode" of the unit, between 'A' and 'P'.
	 @param unitCode	The "unitcode" of the unit, between 1 and 16.
	 
	 @exception IllegalArgumentException if either parameter is out of range.
	 **************************************************************************/
 	public static X10Command 
 	makeOnCommand(char houseCode, int unitCode) {
 		return new X10Command(getHouseBits(houseCode) | 
 									getUnitBits(unitCode) | 
 									ON_BITS,
 								"" + houseCode + unitCode + "_ON");
 	}

	/***************************************************************************
	 Make a command instructing a particular unit to turn off.
	 
	 @param houseCode	The "housecode" of the unit, between 'A' and 'P'.
	 @param unitCode	The "unitcode" of the unit, between 1 and 16.
	 
	 @exception IllegalArgumentException if either parameter is out of range.
	 **************************************************************************/
 	public static X10Command 
 	makeOffCommand(char houseCode, int unitCode) {
 		return new X10Command(getHouseBits(houseCode) | 
 									getUnitBits(unitCode) | 
 									OFF_BITS,
 								"" + houseCode + unitCode + "_OFF");
 	}

	/***************************************************************************
	 Make a dim 5% command, which applies to the last unit which was instructed
	 to turn on with the given housecode. 5% is what the manual specifies; I 
	 find that about ten of these commands make my bulb subjectively dark.
	 
	 @param houseCode	The "housecode" of the unit, between 'A' and 'P'.
	 **************************************************************************/
 	public static X10Command 
 	makeDimCommand(char houseCode) {
 		return new X10Command(getHouseBits(houseCode) | DIM_BITS,
 								houseCode + "_DIM");
 	}

	/***************************************************************************
	 Make a bright 5% command, which applies to the last unit which was 
	 instructed to turn on with the given housecode.
	 
	 @param houseCode	The "housecode" of the unit, between 'A' and 'P'.
	 **************************************************************************/
 	public static X10Command 
 	makeBrightCommand(char houseCode)  {
 		return new X10Command(getHouseBits(houseCode) | BRIGHT_BITS,
 								houseCode + "_BRIGHT");
 	}

	/***************************************************************************
	 Constructs a command from a String, not currently used.
	 
	 @param bits	An String, which should contain exactly 16 '0' and '1' 
	 				digits (as well as any number of "comment" chars).
	 **************************************************************************/
	private
	X10Command(String bits, String description) {
		m_bits = bits;
		m_description = description;
	}
	
	/***************************************************************************
	 Make a command from the bits encoded in the low 16 bits of an
	 integer, from bit 15 to bit 0 (LSB).
	 
	 @param bits	An integer containing a command bit stream 
	 				in its low two bytes.
	 **************************************************************************/
	private 
	X10Command(int bits, String description) {
		m_description = description;
	
		StringBuffer buf = new StringBuffer();
		final int SHORT_HIGH_BIT = 0x8000;
		for (int i = 0; i < 8; i++) {
			buf.append(((bits & SHORT_HIGH_BIT) != 0) ? '1' : '0');
			bits <<= 1;
		}
		buf.append(' ');
		for (int i = 0; i < 8; i++) {
			buf.append(((bits & SHORT_HIGH_BIT) != 0) ? '1' : '0');
			bits <<= 1;
		}
		m_bits = buf.toString();
	}
	
// ACCESSORS
	/***************************************************************************
	 Produces a human-readable representation of the command.
	 
	 @return 	Something along the lines of "G12_ON" or "E1_OFF" or "C_BRIGHT"
	 			or "A_DIM".
	 **************************************************************************/
	public String
	toString() {
		return m_description;
	}
	
	/***************************************************************************
	 Returns the sequence of bits to be sent to the CM17A.
	 
	 @return 	A String containing eight '1's and '0's, a space, and then 
	 			eight more digits.
	 **************************************************************************/
	public String
	getBits() {
		return m_bits;
	}
	
// INTERNAL
	// Note: use int (not short) for all these to avoid sign difficulties
	private static final int ON_BITS = 0x0000;
	private static final int OFF_BITS = 0x0020;

	private static final int BRIGHT_BITS = 0x0088;
	private static final int DIM_BITS = 0x0098;
	
	private static final int A_BITS = 0x6000;
	private static final int B_BITS = 0x7000;
	private static final int C_BITS = 0x4000;
	private static final int D_BITS = 0x5000;
	private static final int E_BITS = 0x8000;
	private static final int F_BITS = 0x9000;
	private static final int G_BITS = 0xA000;
	private static final int H_BITS = 0xB000;
	private static final int I_BITS = 0xE000;
	private static final int J_BITS = 0xF000;
	private static final int K_BITS = 0xC000;
	private static final int L_BITS = 0xD000;
	private static final int M_BITS = 0x0000;
	private static final int N_BITS = 0x1000;
	private static final int O_BITS = 0x2000;
	private static final int P_BITS = 0x3000;
	
	private static final int _1_BITS = 0x0000;
	private static final int _2_BITS = 0x0010;
	private static final int _3_BITS = 0x0008;
	private static final int _4_BITS = 0x0018;
	private static final int _5_BITS = 0x0040;
	private static final int _6_BITS = 0x0050;
	private static final int _7_BITS = 0x0048;
	private static final int _8_BITS = 0x0058;

	private static final int PLUS_8_BITS = 0x0400;

	private static final int[] HOUSE_CODES = {
			A_BITS, B_BITS, C_BITS, D_BITS, 
			E_BITS, F_BITS, G_BITS, H_BITS, 
			I_BITS, J_BITS, K_BITS, L_BITS, 
			M_BITS, N_BITS, O_BITS, P_BITS,
			};

	private static final int[] UNIT_CODES = {
			_1_BITS, _2_BITS, _3_BITS, _4_BITS, _5_BITS, _6_BITS, _7_BITS, _8_BITS, 
			_1_BITS | PLUS_8_BITS, _2_BITS | PLUS_8_BITS, 
			_3_BITS | PLUS_8_BITS, _4_BITS | PLUS_8_BITS, 
			_5_BITS | PLUS_8_BITS, _6_BITS | PLUS_8_BITS, 
			_7_BITS | PLUS_8_BITS, _8_BITS | PLUS_8_BITS,
			};

	private static int
	getHouseBits(char houseCode) {
		if (houseCode < 'A' || houseCode > 'P') {
			throw new IllegalArgumentException();
		}
		return HOUSE_CODES[(int) (houseCode - 'A')];
	}
	
	private static int
	getUnitBits(int unit) {
		if (unit < 1 || unit > 16) {
			throw new IllegalArgumentException();
		}
		return UNIT_CODES[unit-1];
	}
}