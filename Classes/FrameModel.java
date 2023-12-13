package Classes;

public class FrameModel {
    private int index;
    private String identifier;
    private String dlc;
    private String data;
    private String frameName;

    public FrameModel(int index, String identifier, String dlc, String data, String frameName) {
		this.index = index;
		this.identifier = identifier;
		this.dlc = dlc;
		this.data = data;
		this.frameName = frameName;
	}
    
    
    public FrameModel() {

	}


	public int getIndex() {
        return index;
    }


	public void setIndex(int index) {
        this.index = index;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDlc() {
        return dlc;
    }

    public void setDlc(String dlc) {
        this.dlc = dlc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFrameName() {
        return frameName;
    }

    public void setFrameName(String frameName) {
        this.frameName = frameName;
    }
}
