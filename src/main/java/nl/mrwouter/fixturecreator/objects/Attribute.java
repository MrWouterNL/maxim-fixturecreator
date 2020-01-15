package nl.mrwouter.fixturecreator.objects;

public class Attribute {

	private String name;
	private int channel, homeVal, fineChan = -1, minval = -1, maxval = -1;

	public Attribute(String name, int channel, int homeVal) {
		this.name = name;
		this.channel = channel;
		this.homeVal = homeVal;
	}

	public Attribute(String name, int channel, int homeVal, int fineChan) {
		this(name, channel, homeVal);
		this.fineChan = fineChan;
	}

	public Attribute(String name, int channel, int homeVal, int fineChan, int minval, int maxval) {
		this(name, channel, homeVal, fineChan);
		this.minval = minval;
		this.maxval = maxval;
	}

	public String getName() {
		return name;
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
}
