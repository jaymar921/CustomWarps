package me.JayMar921.Warps.VersionSupport;

public class VersionSupport {

	private boolean support_1_17 = false;

	
	public VersionSupport(String version) {
		update(version);
	}
	
	private void update(String version) {
		if(version.contains("1.17") || version.contains("1.17.1")) {
			support_1_17 = true;
		}
		
	}
	
	
	public boolean support_1_17() {
		return support_1_17;
	}
}
