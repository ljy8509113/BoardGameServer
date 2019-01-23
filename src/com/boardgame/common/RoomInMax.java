package com.boardgame.common;

import com.boardgame.davincicode.common.DavinciCommon;

public enum RoomInMax {
	MAX{
		public int getValue(int gameNo) {
			switch(gameNo) {
			case DavinciCommon.GAME_DAVINCICODE:
				return 4;
			}
			return 0;
		}
	};
	
	public abstract int getValue(int gameNo);
}
