package mainPackage.data;

/**
 * CodePack is a useful class that contains all the data collected by an MetricsExtractor Thread
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */public class CodePack extends ExtractorPack {

	public CodePack(FileListenerPack fileListenerPack) {
		this.functionsMetrics = fileListenerPack.getFunctionPacks();
		this.fields = fileListenerPack.getFields();
		this.fileName = fileListenerPack.getFileName();
		this.fileType = fileListenerPack.getFileType();
		this.LOC = fileListenerPack.getLOC();
		this.NOM = fileListenerPack.getNOM();
		this.NPM = fileListenerPack.getNPM();
		this.NOF = fileListenerPack.getNOF();
		this.NPPF = fileListenerPack.getNPPF();
		this.commentLines = 0;
		this.blankLines = 0;
		this.DAM = 0;
		this.avgCC = 0;
		this.AMC = 0;
		this.MOA = 0;
		this.RFC = 0;
		this.CAM = 0;
		this.LCOM3 = 0;
		for(FunctionListenerPack pack : this.functionsMetrics){
			pack.setClassName(fileName);
		}
	}

	/**
	 * @return a string to describe all collected data
	 */
	@Override
	public String toString() {
		return this.fileName + " " + this.fileType +
				"\nComment lines: " + this.commentLines +
				"\nBlank lines: " + this.blankLines +
				"\nLOC: " + this.LOC +
				"\nNOM: " + this.NOM +
				"\nNPM: " + this.NPM +
				"\nWMC: " + this.WMC +
				"\nCC: " + this.avgCC +
				"\nAMC: " + this.AMC +
				"\nDAM: " + this.DAM +
				"\nMOA: " + this.MOA +
				"\nRFC: " + this.RFC +
				"\nCAM: " + this.CAM +
				"\nLCOM3: " + this.LCOM3;
	}


	@Override
	public String toCsvString(String separator) {
		return "";
	}

	@Override
	public void computeDIT() {}

	@Override
	public void computeEfferentCoupledTypes() {}

	@Override
	public Class<?> getClassObject() {return null;}
}
