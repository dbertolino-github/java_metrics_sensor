package mainPackage.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * FileListenerPack is a useful class that contains all the data collected by the FileListener of parse tree walker.
 * LICENSE: Apache-2.0
 * @author Dario Bertolino
 * @since 26/02/2017
 */
public class FileListenerPack extends DataPack {

	public FileListenerPack(){
		this.functionsMetrics = new ArrayList<FunctionListenerPack>();
		this.fields = new HashMap<String, ArrayList<String>>();
		this.fileName = "noName";
		this.fileType = "noType";
		this.NOM = 0;
		this.NPM = 0;
		this.LOC = 0;
		this.NOF = 0;
		this.NPPF = 0;

	}

	public int getNOF() {
		return NOF;
	}

	public void addNOF(int x) {
		this.NOF = this.NOF + x;
	}

	public int getNPPF() {
		return NPPF;
	}

	public void addNPPF(int x) {
		this.NPPF = this.NPPF + x;
	}

	public void addFields(String key, ArrayList<String> names){
		if(this.fields.containsKey(key)){
			for(String n : names){
				this.fields.get(key).add(n);
			}
		}else{
			this.fields.put(key, names);
		}
	}

}
