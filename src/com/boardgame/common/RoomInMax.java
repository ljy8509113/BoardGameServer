package com.boardgame.common;

public enum RoomInMax {
//	DAVINCICODE(Common.GAME_DAVINCICODE);
	MAX{
		public int getValue(int gameNo) {
			switch(gameNo) {
			case Common.GAME_DAVINCICODE :
				return 4;
			}
			return 0;
		}
	};
	
	public abstract int getValue(int gameNo);
}
