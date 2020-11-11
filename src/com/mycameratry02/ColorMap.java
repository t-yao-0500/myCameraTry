package com.mycameratry02;

import android.graphics.Color;
import android.util.Log;



public class ColorMap {
	public static int codeRescale(int data,int threadholdU,int threadholdD){
		data&=0xffffff;
		int rData=data&0xff0000;
		int gData=data&0xff00;
		int bData=data&0xff;
		int rThreadD=(threadholdD&0xff0000)/0x10000;
		int gThreadD=(threadholdD&0xff00)/0x100;
		int bThreadD=threadholdD&0xff;
		int rThreadU=(threadholdU&0xff0000)/0x10000;
		int gThreadU=(threadholdU&0xff00)/0x100;
		int bThreadU=threadholdU&0xff;
		
		double rDelta=(rThreadD+rThreadU)*1.0/2-0x80;
		int rRescale=(int) Math.round((rData-rDelta)*0xff/(rThreadD-rThreadU));
		double gDelta=(gThreadD+gThreadU)*1.0/2-0x80;
		int gRescale=(int) Math.round((gData-gDelta)*0xff/(gThreadD-gThreadU));
		double bDelta=(bThreadD+bThreadU)*1.0/2-0x80;
		int bRescale=(int) Math.round((bData-bDelta)*0xff/(bThreadD-bThreadU));
		return rRescale*0x10000+gRescale*0x100+bRescale;
	}
	
	public static String decode(int data,int threadholdU,int threadholdD){
		data=codeRescale(data,threadholdU,threadholdD);
		return decode(data);
	}
	
	public static String decode(int data){
		data&=0xffffff;
		String color=decodeInt2String(data);
		if (!color.equals("no"))
			return color;
		color=decodeInt2String(nearDecodeInt2Int(data));
		if (!color.equals("no"))
			return "close_to_"+color;
		color=decodeInt2String(farDecodeInt2Int(data));
		return "Far_close_to_"+color;
		
	}
	
	
	public static String decodeInt2String(int data){
		data&=0xffffff;
		switch (data){
		case 0x000000:return "Black";
		case 0x6B8E23:return "OliveDrab_橄榄褐色";
		case 0x808080:return "Gray";
		case 0xEE82EE:return "Violet_紫罗兰";
		case 0x4682B4:return "SteelBlue_铁青";
		case 0x800080:return "Purple_紫色";
		case 0xFFA500:return "Orange1";
		case 0xFFFF00:return "Yellow1";
		case 0x008000:return "Green";
		case 0xFFC0CB:return "pink_粉色";
		case 0xA52A2A:return "Brown_棕色";
		case 0x800000:return "Maroon_栗色";
		case 0xFFFFFF:return "White";
		case 0xF0F8FF:return "AliceBlue_爱丽丝蓝";
		case 0x7FFFD4:return "Aquamarine1_宝石碧绿";
		case 0xF5F5DC:return "Beige_米色（灰棕色）";
		case 0x0000FF:return "Blue_蓝色";
		case 0x5F9EA0:return "CadetBlue_军服蓝";
		case 0x008B8B:return "DarkCyan_暗青色";
		case 0xF0FFFF:return "Azure1_蔚蓝色";
		case 0xFFE4C4:return "Bisque1_橘黄色";
		case 0xFFEBCD:return "BlanchedAlmond_白杏仁色";
		case 0x8A2BE2:return "BlueViolet_蓝紫罗兰";
		case 0xDEB887:return "Burlywood_实木色";
		case 0x7FFF00:return "Chartreuse1_查特酒绿（黄绿色）";
		case 0xFF7F50:return "Coral_珊瑚色";
		case 0xFFF8DC:return "Cornsilk1_米绸色";
		case 0x00008B:return "DarkBlue";
		case 0xB8860B:return "DarkGoldenrod_暗菊黄";
		case 0xFF1493:return "DeepPink1_深粉红";
		case 0x556B2F:return "DarkOliveGreen_暗橄榄绿";
		case 0x9932CC:return "DarkOrchid_深兰花紫";
		case 0xE9967A:return "DarkSalmon_深鲜肉色";
		case 0x483D8B:return "DarkSlateBlue_暗板岩蓝";
		case 0x00CED1:return "DarkTurquoise_暗绿宝石";
		case 0x228B22:return "ForestGreen";
		case 0x696969:return "DimGray_暗灰";
		case 0xB22222:return "FireBrick_耐火砖";
		case 0x006400:return "darkgreen";
		case 0x8B008B:return "DarkMagenta_深洋红";
		case 0x1E90FF:return "DodgerBlue1_道奇蓝";
		case 0x8B0000:return "DarkRed";
		case 0x8FBC8F:return "DarkSeaGreen_暗海洋绿";
		case 0x2F4F4F:return "DarkSlateGray_暗板岩灰";
		case 0x9400D3:return "DarkViolet_深紫罗兰";
		case 0xFFFAF0:return "FloralWhite_花的白色";
		case 0xDCDCDC:return "Gainsboro_庚斯博罗灰色";
		case 0x00BFFF:return "DeepSkyBlue1_深天蓝";
		case 0xF0FFF0:return "Honeydew1_蜜瓜色";
		case 0xCD5C5C:return "IndianRed_印度红";
		case 0xF0E68C:return "Khaki_卡叽布";
		case 0xE0FFFF:return "LightCyan1_淡青色";
		case 0xFFF0F5:return "LavenderBlush1_淡紫红";
		case 0xFFFACD:return "LemonChiffon1_柠檬绸色";
		case 0xF08080:return "LightCoral_淡珊瑚色";
		case 0xFFB6C1:return "LightPink_浅粉色";
		case 0xFFD700:return "Gold1";
		case 0xADFF2F:return "greenyellow";
		case 0xFF69B4:return "HotPink_热情的粉红";
		case 0xFFFFF0:return "Ivory1_象牙";
		case 0xE6E6FA:return "Lavender_薰衣草";
		case 0x7CFC00:return "LawnGreen_草坪绿";
		case 0xADD8E6:return "LightBlue";
		case 0xD3D3D3:return "LightGrey";
		case 0x87CEFA:return "LightSkyBlue_亮天蓝色";
		case 0x20B2AA:return "LightSeaGreen_浅海洋绿";
		case 0xB0C4DE:return "LightSteelBlue_亮宝蓝"; 
		case 0x32CD32:return "LimeGreen_闪光绿";
		case 0xF5FFFA:return "MintCream_薄荷奶油";
		case 0xFFE4B5:return "Moccasin_鹿皮靴";
		case 0x66CDAA:return "MediumAquamarine_中宝石碧绿";
		case 0xBA55D3:return "MediumOrchid_中兰花紫";
		case 0x3CB371:return "MediumSeaGreen_中海洋绿";
		case 0x00FA9A:return "MediumSpringGreen";
		case 0x778899:return "LightSlateGray_亮板岩灰";
		case 0xFAF0E6:return "Linen_亚麻布";
		case 0x48D1CC:return "MediumTurquoise_中绿宝石";
		case 0x0000CD:return "MediumBlue";
		case 0xC71585:return "MediumVioletRed_中紫罗兰红";
		case 0x7B68EE:return "MediumSlateBlue_中板岩蓝";
		case 0xFFE4E1:return "MistyRose1_薄雾玫瑰";
		case 0xFFDEAD:return "NavajoWhite1_土著白";
		case 0x000080:return "NavyBlue_海军蓝";
		case 0xFDF5E6:return "OldLace_旧蕾丝";
		case 0x98FB98:return "palegreen";
		case 0xDB7093:return "PaleVioletRed_弱紫罗兰红";
		case 0xFFDAB9:return "PeachPuff1_桃肉色";
		case 0xB0E0E6:return "PowderBlue_火药青";
		case 0x191970:return "midnightblue";
		case 0x808000:return "Olive_橄榄绿";
		case 0xDA70D6:return "Orchid_兰花紫";
		case 0xAFEEEE:return "PaleTurquoise_弱绿宝石";
		case 0xFFEFD5:return "Papayawhip_番木瓜";
		case 0xCD853F:return "Peru_秘鲁";
		case 0xDDA0DD:return "Plum_李子紫";
		case 0x4169E1:return "RoyalBlue_宝蓝";
		case 0xBC8F8F:return "RosyBrown_玫瑰棕色";
		case 0xFA8072:return "Salmon_鲑鱼肉色";
		case 0xA0522D:return "Sienna_黄土赭色";
		case 0x6A5ACD:return "SlateBlue_板岩蓝";
		case 0xFFFAFA:return "Snow1_雪白";
		case 0xC0C0C0:return "Silver_银灰色";
		case 0xD8BFD8:return "Thistle_蓟";
		case 0x40E0D0:return "Turquoise_绿宝石";
		case 0x8B4513:return "SaddleBrown_马鞍棕色";
		case 0xF4A460:return "SandyBrown_沙棕色";
		case 0xFFF5EE:return "Seashell1_海贝壳";
		case 0x87CEEB:return "SkyBlue_天蓝色";
		case 0x708090:return "SlateGray_板岩灰";
		case 0x00FF7F:return "SpringGreen1";
		case 0xD2B48C:return "Tan3_茶色";
		case 0x008080:return "Teal_水鸭色";
		case 0xF5F5F5:return "WhiteSmoke_白烟";
		case 0xF5DEB3:return "Wheat_小麦色";
		case 0x9ACD32:return "yellowgreen";
		case 0xDC143C:return "crimson_猩红（深红）";
		case 0xF8F8FF:return "GhostWhite";
		case 0xFAEBD7:return "AntiqueWhite_古董白";
		case 0x00FFFF:return "Cyan1_青色";
		case 0xD2691E:return "Chocolate_巧克力色";
		case 0x6495ED:return "CornflowerBlue_矢车菊蓝";
		case 0xBEBEBE:return "Grey";
		case 0x8470FF:return "LightSlateBlue_亮板岩蓝";
		case 0xBDB76B:return "DarkKhaki_深卡叽布";
		case 0xEEE8AA:return "PaleGoldenrod_灰菊黄";
		case 0xEEDD82:return "LightGoldenrod_亮菊黄";
		case 0xDAA520:return "Goldenrod_金菊黄";
		case 0xFF8C00:return "DarkOrange";
		case 0xFF4500:return "OrangeRed1_橙红色";
		case 0xB03060:return "Maroon_栗色";
		case 0xD02090:return "VioletRed_紫罗兰红";
		case 0xA020F0:return "Purple";
		case 0x9370DB:return "MediumPurple";
		case 0xEEE9E9:return "Snow2_雪白";
		case 0xCDC9C9:return "Snow3_雪白";
		case 0x8B8989:return "Snow4_雪白";
		case 0xEEE5DE:return "Seashell2_海贝壳";
		case 0xCDC5BF:return "Seashell3_海贝壳";
		case 0x8B8682:return "Seashell4_海贝壳";
		case 0xFFEFDB:return "AntiqueWhite1_古董白";
		case 0xEEDFCC:return "AntiqueWhite2_古董白";
		case 0xCDC0B0:return "AntiqueWhite3_古董白";
		case 0x8B8378:return "AntiqueWhite4_古董白";
		case 0xEED5B7:return "Bisque2_橘黄色";
		case 0xCDB79E:return "Bisque3_橘黄色";
		case 0x8B7D6B:return "Bisque4_橘黄色";
		case 0xEECBAD:return "PeachPuff2_桃肉色";
		case 0xCDAF95:return "PeachPuff3_桃肉色";
		case 0x8B7765:return "PeachPuff4_桃肉色";
		case 0xEECFA1:return "NavajoWhite2_土著白";
		case 0xCDB38B:return "NavajoWhite3_土著白";
		case 0x8B795E:return "NavajoWhite4_土著白";
		case 0xEEE9BF:return "LemonChiffon2_柠檬绸色";
		case 0xCDC9A5:return "LemonChiffon3_柠檬绸色";
		case 0x8B8970:return "LemonChiffon4_柠檬绸色";
		case 0xCDC8B1:return "Cornsilk3_米绸色";
		case 0xEEE8CD:return "Cornsilk2_米绸色";
		case 0x8B8878:return "Cornsilk4_米绸色";
		case 0xEEEEE0:return "Ivory2_象牙";
		case 0xCDCDC1:return "Ivory3_象牙";
		case 0x8B8B83:return "Ivory4_象牙";
		case 0xE0EEE0:return "Honeydew2_蜜瓜色";
		case 0xC1CDC1:return "Honeydew3_蜜瓜色";
		case 0x838B83:return "Honeydew4_蜜瓜色";
		case 0xEEE0E5:return "LavenderBlush2_淡紫红";
		case 0xCDC1C5:return "LavenderBlush3_淡紫红";
		case 0x8B8386:return "LavenderBlush4_淡紫红";
		case 0xEED5D2:return "MistyRose2_薄雾玫瑰";
		case 0xCDB7B5:return "MistyRose3_薄雾玫瑰";
		case 0x8B7D7B:return "MistyRose4_薄雾玫瑰";
		case 0xE0EEEE:return "Azure2_蔚蓝色";
		case 0xC1CDCD:return "Azure3_蔚蓝色";
		case 0x838B8B:return "Azure4_蔚蓝色";
		case 0x836FFF:return "SlateBlue1_板岩蓝";
		case 0x7A67EE:return "SlateBlue2_板岩蓝";
		case 0x6959CD:return "SlateBlue3_板岩蓝";
		case 0x473C8B:return "SlateBlue4_板岩蓝";
		case 0x4876FF:return "RoyalBlue1";
		case 0x436EEE:return "RoyalBlue2";
		case 0x3A5FCD:return "RoyalBlue3";
		case 0x27408B:return "RoyalBlue4";
		case 0x0000EE:return "Blue2";
		case 0x1C86EE:return "DodgerBlue2_道奇蓝";
		case 0x1874CD:return "DodgerBlue3_道奇蓝";
		case 0x104E8B:return "DodgerBlue4_道奇蓝";
		case 0x63B8FF:return "SteelBlue1_铁青";
		case 0x5CACEE:return "SteelBlue2_铁青";
		case 0x4F94CD:return "SteelBlue3_铁青";
		case 0x36648B:return "SteelBlue4_铁青";
		case 0x00B2EE:return "DeepSkyBlue2_深天蓝";
		case 0x009ACD:return "DeepSkyBlue3_深天蓝";
		case 0x00688B:return "DeepSkyBlue4_深天蓝";
		case 0x87CEFF:return "SkyBlue1_天蓝色";
		case 0x7EC0EE:return "SkyBlue2_天蓝色";
		case 0x6CA6CD:return "SkyBlue3_天蓝色";
		case 0x4A708B:return "SkyBlue4_天蓝色";
		case 0xB0E2FF:return "LightSkyBlue1_亮天蓝色";
		case 0xA4D3EE:return "LightSkyBlue2_亮天蓝色";
		case 0x8DB6CD:return "LightSkyBlue3_亮天蓝色";
		case 0x607B8B:return "LightSkyBlue4_亮天蓝色";
		case 0xC6E2FF:return "SlateGray1_板岩灰";
		case 0xB9D3EE:return "SlateGray2_板岩灰";
		case 0x9FB6CD:return "SlateGray3_板岩灰";
		case 0x6C7B8B:return "SlateGray4_板岩灰";
		case 0xCAE1FF:return "LightSteelBlue1_亮宝蓝";
		case 0xBCD2EE:return "LightSteelBlue2_亮宝蓝";
		case 0xA2B5CD:return "LightSteelBlue3_亮宝蓝";
		case 0x6E7B8B:return "LightSteelBlue4_亮宝蓝";
		case 0xBFEFFF:return "LightBlue1";
		case 0xB2DFEE:return "LightBlue2";
		case 0x9AC0CD:return "LightBlue3";
		case 0x68838B:return "LightBlue4";
		case 0xD1EEEE:return "LightCyan2_淡青色";
		case 0xB4CDCD:return "LightCyan3_淡青色";
		case 0x7A8B8B:return "LightCyan4_淡青色";
		case 0xBBFFFF:return "PaleTurquoise1_弱绿宝石";
		case 0xAEEEEE:return "PaleTurquoise2_弱绿宝石";
		case 0x96CDCD:return "PaleTurquoise3_弱绿宝石";
		case 0x668B8B:return "PaleTurquoise4_弱绿宝石";
		case 0x98F5FF:return "CadetBlue1_军服蓝";
		case 0x8EE5EE:return "CadetBlue2_军服蓝";
		case 0x7AC5CD:return "CadetBlue3_军服蓝";
		case 0x53868B:return "CadetBlue4_军服蓝";
		case 0x00F5FF:return "Turquoise1_绿宝石";
		case 0x00E5EE:return "Turquoise2_绿宝石";
		case 0x00C5CD:return "Turquoise3_绿宝石";
		case 0x00868B:return "Turquoise4_绿宝石";
		case 0x00EEEE:return "Cyan2_青色";
		case 0x00CDCD:return "Cyan3_青色";
		case 0x97FFFF:return "DarkSlateGray1_暗板岩灰";
		case 0x8DEEEE:return "DarkSlateGray2_暗板岩灰";
		case 0x79CDCD:return "DarkSlateGray3_暗板岩灰";
		case 0x528B8B:return "DarkSlateGray4_暗板岩灰";
		case 0x76EEC6:return "Aquamarine2_宝石碧绿";
		case 0x458B74:return "Aquamarine4_宝石碧绿";
		case 0xC1FFC1:return "DarkSeaGreen1_暗海洋绿";
		case 0xB4EEB4:return "DarkSeaGreen2_暗海洋绿";
		case 0x9BCD9B:return "DarkSeaGreen3_暗海洋绿";
		case 0x698B69:return "DarkSeaGreen4_暗海洋绿";
		case 0x54FF9F:return "SeaGreen1_海洋绿";
		case 0x4EEE94:return "SeaGreen2_海洋绿";
		case 0x43CD80:return "SeaGreen3_海洋绿";
		case 0x2E8B57:return "SeaGreen4_海洋绿";
		case 0x9AFF9A:return "PaleGreen1";
		case 0x90EE90:return "LightGreen";
		case 0x7CCD7C:return "PaleGreen3";
		case 0x548B54:return "PaleGreen4";
		case 0x00EE76:return "SpringGreen2";
		case 0x00CD66:return "SpringGreen3";
		case 0x008B45:return "SpringGreen4";
		case 0x00FF00:return "Green1";
		case 0x00EE00:return "Green2";
		case 0x00CD00:return "Green3";
		case 0x008B00:return "Green4";
		case 0x76EE00:return "Chartreuse2_查特酒绿（黄绿色）";
		case 0x66CD00:return "Chartreuse3_查特酒绿（黄绿色）";
		case 0x458B00:return "Chartreuse4_查特酒绿（黄绿色）";
		case 0xC0FF3E:return "OliveDrab1_橄榄褐色";
		case 0xB3EE3A:return "OliveDrab2_橄榄褐色";
		case 0x698B22:return "OliveDrab4_橄榄褐色";
		case 0xCAFF70:return "DarkOliveGreen1_暗橄榄绿";
		case 0xBCEE68:return "DarkOliveGreen2_暗橄榄绿";
		case 0xA2CD5A:return "DarkOliveGreen3_暗橄榄绿";
		case 0x6E8B3D:return "DarkOliveGreen4_暗橄榄绿";
		case 0xFFF68F:return "Khaki1_卡叽布";
		case 0xEEE685:return "Khaki2_卡叽布";
		case 0xCDC673:return "Khaki3_卡叽布";
		case 0x8B864E:return "Khaki4_卡叽布";
		case 0xFFEC8B:return "LightGoldenrod1_亮菊黄";
		case 0xEEDC82:return "LightGoldenrod2_亮菊黄";
		case 0xCDBE70:return "LightGoldenrod3_亮菊黄";
		case 0x8B814C:return "LightGoldenrod4_亮菊黄";
		case 0xFFFFE0:return "LightYellow1";
		case 0xEEEED1:return "LightYellow2";
		case 0xCDCDB4:return "LightYellow3";
		case 0x8B8B7A:return "LightYellow4";
		case 0xEEEE00:return "Yellow2";
		case 0xCDCD00:return "Yellow3";
		case 0x8B8B00:return "Yellow4";
		case 0xEEC900:return "Gold2";
		case 0xCDAD00:return "Gold3";
		case 0x8B7500:return "Gold4";
		case 0xFFC125:return "Goldenrod1_金菊黄";
		case 0xEEB422:return "Goldenrod2_金菊黄";
		case 0xCD9B1D:return "DarkGoldenrod3_暗菊黄";
		case 0x8B658B:return "DarkGoldenrod4_暗菊黄";
		case 0xFFC1C1:return "RosyBrown1_玫瑰棕色";
		case 0xEEB4B4:return "RosyBrown2_玫瑰棕色";
		case 0xCD9B9B:return "RosyBrown3_玫瑰棕色";
		case 0x8B6969:return "RosyBrown4_玫瑰棕色";
		case 0xFF6A6A:return "IndianRed1_印度红";
		case 0xEE6363:return "IndianRed2_印度红";
		case 0xCD5555:return "IndianRed3_印度红";
		case 0x8B3A3A:return "IndianRed4_印度红";
		case 0xFF8247:return "Sienna1_黄土赭色";
		case 0xEE7942:return "Sienna2_黄土赭色";
		case 0xCD6839:return "Sienna3_黄土赭色";
		case 0x8B4726:return "Sienna4_黄土赭色";
		case 0xFFD39B:return "Burlywood1_实木色";
		case 0xEEC591:return "Burlywood2_实木色";
		case 0xCDAA7D:return "Burlywood3_实木色";
		case 0x8B7355:return "Burlywood4_实木色";
		case 0xFFE7BA:return "Wheat1";
		case 0xEED8AE:return "Wheat2";
		case 0xCDBA96:return "Wheat3";
		case 0x8B7E66:return "Wheat4";
		case 0xFFA54F:return "Tan1_茶色";
		case 0xEE9A49:return "Tan2_茶色";
		case 0x8B5A2B:return "Tan4_茶色";
		case 0xFF7F24:return "Chocolate1";
		case 0xEE7621:return "Chocolate2";
		case 0xCD661D:return "Chocolate3";
		case 0xFF3030:return "Firebrick1_耐火砖";
		case 0xEE2C2C:return "Firebrick2_耐火砖";
		case 0xCD2626:return "Firebrick3_耐火砖";
		case 0x8B1A1A:return "Firebrick4_耐火砖";
		case 0xFF4040:return "Brown1_棕色";
		case 0xEE3B3B:return "Brown2_棕色";
		case 0xCD3333:return "Brown3_棕色";
		case 0x8B2323:return "Brown4_棕色";
		case 0xFF8C69:return "Salmon1_鲜肉色";
		case 0xEE8262:return "Salmon2_鲜肉色";
		case 0xCD7054:return "Salmon3_鲜肉色";
		case 0x8B4C39:return "Salmon4_鲜肉色";
		case 0xFFA07A:return "LightSalmon1_浅鲑鱼肉色";
		case 0xEE9572:return "LightSalmon2_浅鲑鱼肉色";
		case 0xCD8162:return "LightSalmon3_浅鲑鱼肉色";
		case 0x8B5742:return "LightSalmon4_浅鲑鱼肉色";
		case 0xEE9A00:return "Orange2";
		case 0xCD8500:return "Orange3";
		case 0x8B5A00:return "Orange4";
		case 0xFF7F00:return "DarkOrange1";
		case 0xEE7600:return "DarkOrange2";
		case 0xCD6600:return "DarkOrange3";
		case 0x8B4500:return "DarkOrange4";
		case 0xFF7256:return "Coral1_珊瑚色";
		case 0xEE6A50:return "Coral2_珊瑚色";
		case 0xCD5B45:return "Coral3_珊瑚色";
		case 0x8B3E2F:return "Coral4_珊瑚色";
		case 0xFF6347:return "Tomato1_番茄红";
		case 0xEE5C42:return "Tomato2_番茄红";
		case 0xCD4F39:return "Tomato3_番茄红";
		case 0x8B3626:return "Tomato4_番茄红";
		case 0xEE4000:return "OrangeRed2_橙红色";
		case 0xCD3700:return "OrangeRed3_橙红色";
		case 0x8B2500:return "OrangeRed4_橙红色";
		case 0xFF0000:return "Red1";
		case 0xEE0000:return "Red2";
		case 0xCD0000:return "Red3";
		case 0xCD1076:return "DeepPink3";
		case 0xEE1289:return "DeepPink2";
		case 0x8B0A50:return "DeepPink4";
		case 0xFF6EB4:return "HotPink1";
		case 0xEE6AA7:return "HotPink2";
		case 0xCD6090:return "HotPink3";
		case 0x8B3A62:return "HotPink4";
		case 0xFFB5C5:return "Pink1";
		case 0xEEA9B8:return "Pink2";
		case 0xCD919E:return "Pink3";
		case 0x8B636C:return "Pink4";
		case 0xFFAEB9:return "LightPink1";
		case 0xEEA2AD:return "LightPink2";
		case 0xCD8C95:return "LightPink3";
		case 0x8B5F65:return "LightPink4";
		case 0xFF82AB:return "PaleVioletRed1_苍紫罗兰红";
		case 0xEE799F:return "PaleVioletRed2_苍紫罗兰红";
		case 0xCD6889:return "PaleVioletRed3_苍紫罗兰红";
		case 0x8B475D:return "PaleVioletRed4_苍紫罗兰红";
		case 0xFF34B3:return "Maroon1_栗色";
		case 0xEE30A7:return "Maroon2_栗色";
		case 0xCD2990:return "Maroon3_栗色";
		case 0x8B1C62:return "Maroon4_栗色";
		case 0xFF3E96:return "VioletRed1_紫罗兰红";
		case 0xEE3A8C:return "VioletRed2_紫罗兰红";
		case 0xCD3278:return "VioletRed3_紫罗兰红";
		case 0x8B2252:return "VioletRed4_紫罗兰红";
		case 0xFF00FF:return "Magenta1_洋红";
		case 0xEE00EE:return "Fuchsia_灯笼海藻（紫红色）";
		case 0xCD00CD:return "Magenta3_洋红";
		case 0xFF83FA:return "Orchid1_兰花紫";
		case 0xEE7AE9:return "Orchid2_兰花紫";
		case 0xCD69C9:return "Orchid3_兰花紫";
		case 0x8B4789:return "Orchid4_兰花紫";
		case 0xFFBBFF:return "Plum1";
		case 0xEEAEEE:return "Plum2";
		case 0xCD96CD:return "Plum3";
		case 0x8B668B:return "Plum4";
		case 0xE066FF:return "MediumOrchid1_中兰花紫";
		case 0xD15FEE:return "MediumOrchid2_中兰花紫";
		case 0xB452CD:return "MediumOrchid3_中兰花紫";
		case 0x7A378B:return "MediumOrchid4_中兰花紫";
		case 0xBF3EFF:return "DarkOrchid1_深兰花紫";
		case 0xB23AEE:return "DarkOrchid2_深兰花紫";
		case 0x9A32CD:return "DarkOrchid3_深兰花紫";
		case 0x68228B:return "DarkOrchid4_深兰花紫";
		case 0x9B30FF:return "Purple1";
		case 0x912CEE:return "Purple2";
		case 0x7D26CD:return "Purple3";
		case 0x551A8B:return "Purple4";
		case 0xAB82FF:return "MediumPurple1";
		case 0x9F79EE:return "MediumPurple2";
		case 0x8968CD:return "MediumPurple3";
		case 0x5D478B:return "MediumPurple4";
		case 0xFFE1FF:return "Thistle1";
		case 0xEED2EE:return "Thistle2";
		case 0xCDB5CD:return "Thistle3";
		case 0x8B7B8B:return "Thistle4";
		case 0x1C1C1C:return "grey11";
		case 0x363636:return "grey21";
		case 0x4F4F4F:return "grey31";
		case 0x828282:return "grey51";
		case 0x9C9C9C:return "grey61";
		case 0xB5B5B5:return "grey71";
		case 0xCFCFCF:return "gray81";
		case 0xE8E8E8:return "gray91";
		case 0x4B0082:return "Indigo_靛青色";
		case 0xFFB3A7:return "粉红";
		case 0xF00056:return "品红";
		case 0xED5736:return "妃红色";
		case 0xF47983:return "桃红";
		case 0xDB5A6B:return "海棠红";
		case 0xF20C00:return "石榴红";
		case 0xC93756:return "樱桃色";
		case 0xF05654:return "银红";
		case 0xFF2121:return "大红";
		case 0x8C4356:return "绛紫";
		case 0xC83C23:return "绯红";
		case 0x9D2933:return "胭脂";
		case 0xFF4C00:return "朱红";
		case 0xFF4E20:return "丹";
		case 0xF35336:return "彤赤色";
		case 0xCB3A56:return "茜色";
		case 0xFF2D51:return "火红";
		case 0xC91F37:return "赫赤";
		case 0xEF7A82:return "嫣红";
		case 0xFF0097:return "洋红";
		case 0xFF3300:return "炎";
		case 0xC3272B:return "赤";
		case 0xA98175:return "绾";
		case 0xC32136:return "枣红";
		case 0xB36D61:return "檀";
		case 0xDC3023:return "酡红";
		case 0xBE002F:return "殷红";
		case 0xF9906F:return "酡颜";
		case 0xFFF143:return "鹅黄";
		case 0xEAFF56:return "樱草色";
		case 0xFAFF72:return "鸭黄";
		case 0xFFA631:return "杏黄";
		case 0xFF8C31:return "杏红";
		case 0xFFA400:return "橙黄";
		case 0xFFC773:return "姜黄";
		case 0xFA8C35:return "橙色";
		case 0xA88462:return "驼色";
		case 0xFF8936:return "橘黄";
		case 0xFF7500:return "橘红";
		case 0xF0C239:return "缃色";
		case 0xB35C44:return "茶色";
		case 0xC89B40:return "昏黄";
		case 0xB25D25:return "棕色";
		case 0x60281E:return "栗色";
		case 0x827100:return "棕绿";
		case 0x9B4400:return "棕红";
		case 0x955539:return "赭色";
		case 0x6E511E:return "褐色";
		case 0x7C4B00:return "棕黑";
		case 0xAE7000:return "棕黄";
		case 0xCA6924:return "琥珀";
		case 0xD3B17D:return "枯黄";
		case 0xE29C45:return "黄栌";
		case 0xD9B611:return "秋香色";
		case 0xC9DD22:return "柳黄";
		case 0x789262:return "竹青";
		case 0x896C39:return "秋色";
		case 0xBDDD22:return "嫩绿";
		case 0xAFDD22:return "柳绿";
		case 0xA3D900:return "葱黄";
		case 0x9ED900:return "葱绿";
		case 0x0AA344:return "青葱";
		case 0x0C8918:return "绿沉";
		case 0x2ADD9C:return "碧绿";
		case 0x0EB83A:return "葱青";
		case 0x00BC12:return "油绿";
		case 0x1BD1A5:return "碧色";
		case 0x48C0A3:return "青碧";
		case 0x3DE1AD:return "翡翠色";
		case 0x00E09E:return "青色";
		case 0xC0EBD7:return "青白";
		case 0xBBCDC5:return "蟹壳青";
		case 0x40DE5A:return "草绿";
		case 0x00E079:return "青翠";
		case 0xE0EEE8:return "鸭卵青";
		case 0x424C50:return "鸦青";
		case 0x00E500:return "绿色";
		case 0x96CE54:return "豆青";
		case 0x7BCFA6:return "玉色（石青）";
		case 0xA4E2C6:return "艾绿";
		case 0x9ED048:return "豆绿";
		case 0x7FECAD:return "缥";
		case 0x21A675:return "松柏绿";
		case 0x057748:return "松花绿";
		case 0x44CEF6:return "蓝";
		case 0x065279:return "靛蓝";
		case 0x70F3FF:return "蔚蓝";
		case 0xBCE672:return "松花色";
		case 0x177CB0:return "靛青";
		case 0x3EEDE7:return "碧蓝";
		case 0x4B5CC4:return "宝蓝";
		case 0xA1AFC9:return "蓝灰色";
		case 0x3B2E7E:return "藏蓝";
		case 0x426666:return "黛绿";
		case 0x574266:return "黛紫";
		case 0x2E4E7E:return "藏青";
		case 0x4A4266:return "黛";
		case 0x425066:return "黛蓝";
		case 0x8D4BBB:return "紫色";
		case 0x815463:return "紫酱";
		case 0x4C221B:return "紫檀";
		case 0x56004F:return "紫棠";
		case 0x4C8DAE:return "群青";
		case 0x815476:return "酱紫";
		case 0x003371:return "绀青";
		case 0x801DAE:return "青莲";
		case 0xB0A4E3:return "雪青";
		case 0xCCA4E3:return "丁香色";
		case 0xE4C6D0:return "藕荷色";
		case 0x519A73:return "苍黄";
		case 0x7397AB:return "苍黑";
		case 0xEDD1D8:return "藕色";
		case 0x75878A:return "苍色";
		case 0xA29B7C:return "苍青";
		case 0xD1D9E0:return "苍白";
		case 0x88ADA6:return "水色";
		case 0xD4F2E7:return "水绿";
		case 0xD3E0F3:return "淡青";
		case 0xF3D3E7:return "水红";
		case 0xD2F0F4:return "水蓝";
		case 0x30DFF3:return "湖蓝";
		case 0x25F8CB:return "湖绿";
		case 0xFFFBF0:return "像牙白";
		case 0xD6ECF0:return "月白";
		case 0xE0F0E9:return "素";
		case 0xF0FCFF:return "雪白";
		case 0xF2ECDE:return "缟";
		case 0xF3F9F1:return "荼白";
		case 0xE9F1F6:return "霜色";
		case 0xFCEFE8:return "鱼肚白";
		case 0xF0F0F4:return "铅白";
		case 0xC2CCD0:return "花白";
		case 0xE3F9FD:return "莹白";
		case 0xEEDEB0:return "牙色";
		case 0x622A1D:return "玄色";
		case 0x3D3B4F:return "玄青";
		case 0x392F41:return "乌黑";
		case 0x50616D:return "墨色";
		case 0x725E82:return "乌色";
		case 0x161823:return "漆黑";
		case 0x758A99:return "墨灰";
		case 0x493131:return "缁色";
		case 0x312520:return "煤黑";
		case 0x5D513C:return "黧";
		case 0x75664D:return "黎";
		case 0x665757:return "黝黑";
		case 0xF2BE45:return "赤金";
		case 0x6B6882:return "黝";
		case 0x41555D:return "黯";
		case 0xEACD76:return "金色";
		case 0xE9E7EF:return "银白";
		case 0x549688:return "铜绿";
		case 0xBACAC6:return "老银";
		
		default:return "no";
		}
	}
	public static int nearDecodeInt2Int(int data){
		int rData=(data&0xff0000)/0x10000;
		int gData=(data&0xff00)/0x100;
		int bData=data&0xff;
		int dec;
		final int DEC=0x20;
		int dataTemp;
		int r,g,b;
		String color="no";
		for (dec=2;dec<DEC;dec++){
			for (r=0;r<dec;r++){
				for (g=0;g<dec;g++){
					for (b=0;b<dec;b++){
						if (((rData+r)<=0xff)&&((gData+g)<=0xff)&&((bData+b)<=0xff)){
							dataTemp=((rData+r)*0x10000)+((gData+g)*0x100)+(bData+b);
							color=decodeInt2String(dataTemp);
							if (!color.equals("no"))
								return dataTemp;
						}
						if ((rData+r<=0xff)&&(gData+g<=0xff)&&(bData-b>=0)){
							dataTemp=((rData+r)*0x10000)+((gData+g)*0x100)+(bData-b);
							color=decodeInt2String(dataTemp);
							if (!color.equals("no"))
								return dataTemp;
						}
						if ((rData+r<=0xff)&&(gData+g>=0)&&(bData+b<=0xff)){
							dataTemp=((rData+r)*0x10000)+((gData-g)*0x100)+(bData+b);
							color=decodeInt2String(dataTemp);
							if (!color.equals("no"))
								return dataTemp;
						}
						if ((rData+r<=0xff)&&(gData-g>=0)&&(bData-b>=0)){
							dataTemp=((rData+r)*0x10000)+((gData-g)*0x100)+(bData-b);
							color=decodeInt2String(dataTemp);
							if (!color.equals("no"))
								return dataTemp;
						}
						if ((rData-r>=0)&&(gData+g<=0xff)&&(bData+b<=0xff)){
							dataTemp=((rData-r)*0x10000)+((gData+g)*0x100)+(bData+b);
							color=decodeInt2String(dataTemp);
							if (!color.equals("no"))
								return dataTemp;
						}
						if ((rData-r>=0)&&(gData+g<=0xff)&&(bData-b>=0)){
							dataTemp=((rData-r)*0x10000)+((gData+g)*0x100)+(bData-b);
							color=decodeInt2String(dataTemp);
							if (!color.equals("no"))
								return dataTemp;
						}
						if ((rData-r>=0)&&(gData-g>=0)&&(bData+b<=0xff)){
							dataTemp=((rData-r)*0x10000)+((gData-g)*0x100)+(bData+b);
							color=decodeInt2String(dataTemp);
							if (!color.equals("no"))
								return dataTemp;
						}
						if ((rData-r>=0)&&(gData-g>=0)&&(bData-b>=0)){
							dataTemp=((rData-r)*0x10000)+((gData-g)*0x100)+(bData-b);
							color=decodeInt2String(dataTemp);
							if (!color.equals("no"))
								return dataTemp;	
						}
					}
				}
			}
		}
		return 0x1000000;			
	}
	public static int farDecodeInt2Int(int data){
		data&=0xffffff;
		int rData=(data&0xff0000)/0x10000;
		int gData=(data&0xff00)/0x100;
		int bData=data&0xff;
		
		if (rData<0x55)
			rData=0x0;
		else if (rData>0xAB)
			rData=0xFF;
		else
			rData=0x80;
		
		if (gData<0x55)
			gData=0x0;
		else if (gData>0xAB)
			gData=0xFF;
		else
			gData=0x80;
		
		if (bData<0x55)
			bData=0x0;
		else if (bData>0xAB)
			bData=0xFF;
		else
			bData=0x80;
		data=rData*0x10000+gData*0x100+bData;
		
		switch(data){
			case 0x000000:return 0x000000;
			case 0x000080:
			case 0x8080FF:
			case 0x0000FF:return 0x0000FF;
			case 0x00FF00:
			case 0x008000:return 0x00FF00;
			
			case 0x008080:return 0x008080;
			case 0x0080FF:
			case 0x00FF80:
			case 0x00FFFF:return 0x00FFFF;
			case 0x800000:
			case 0xFF0000:return 0xEE0000;
			case 0x8000FF:
			case 0x800080:return 0x800080;
			
			case 0x808000:return 0x808000;
			case 0x808080:return 0x828282;
			
			case 0x80FF00:return 0xEEAEEE;
			case 0x80FF80:
			case 0x80FFFF:return 0xF0FFFF;
			case 0xFF0080:
			case 0xFF00FF:return 0xFF00FF;
			case 0xFF8000:
			case 0xFFFF00:
			case 0xFFFF80:return 0xFFFF00;
			case 0xFF8080:return 0xFF4040;
			case 0xFF80FF:return 0xEE82EE;
			
			case 0xFFFFFF:return 0xFFFFFF;
			default:return 0x1000000;
		}
	}
}
