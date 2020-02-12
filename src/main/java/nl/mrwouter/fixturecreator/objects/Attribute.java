package nl.mrwouter.fixturecreator.objects;

public class Attribute {

	private String name;
	private int channel, homeVal, fineChan = -1, minval = -1, maxval = -1;
	private boolean virtualDimmer;

	public Attribute(String name, int channel, int homeVal) {
		this.name = name;
		this.channel = channel;
		this.homeVal = homeVal;
	}

	public Attribute(String name, int channel, int homeVal, int fineChan) {
		this(name, channel, homeVal);
		this.fineChan = fineChan;
	}

	public Attribute(String name, int channel, int homeVal, int fineChan, int minval, int maxval, boolean virtualDimmer) {
		this(name, channel, homeVal, fineChan);
		this.minval = minval;
		this.maxval = maxval;
		this.virtualDimmer = virtualDimmer;
	}

	public String getName() {
		return name;
	}
	
	public String getPrintableName() {
		return name.replace("\\lt", "l\\t").replace("lt", "@").replace("l\\t", "lt");
	}

	public int getChannel() {
		return channel;
	}

	public int getFineChannel() {
		return fineChan;
	}

	public int getHomeVal() {
		return homeVal;
	}

	public int getMinVal() {
		return minval;
	}

	public int getMaxVal() {
		return maxval;
	}
	
	public boolean hasVirtualDimmer() {
		return virtualDimmer;
	}
}
