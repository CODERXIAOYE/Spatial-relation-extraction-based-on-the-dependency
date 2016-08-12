package DependenceRecognition;

import java.util.List;

import SpatialRelationship.SpatialRelationshipTriple;
import SpatialRelationship.Word;

public class RecognizeHelper {
	/**
	 * ��ȡ���ڵ�
	 * @param allDependences
	 * @param childWord
	 * @return
	 */
	public Word getParentWord(List<Word> allDependences,Word childWord){
		if(childWord.getRelate().equals("HED")){
			return null;
		}
		Word ParentWord=new Word();
		for(int i=0;i<allDependences.size();i++){
			if(allDependences.get(i).getId().equals(childWord.getParent())){
				ParentWord=allDependences.get(i);
			}
		}
		return ParentWord;
	}
/**
 * ��������ʶ��
 * @param resultOfSpatialRelationshipExtract
 * @param handleTriple ��ǰҪ�������Ԫ��
 * @param allDependences
 * @return
 */
	public List<SpatialRelationshipTriple> getAllTrajectorOfTriple(List<SpatialRelationshipTriple> resultOfSpatialRelationshipExtract,SpatialRelationshipTriple handleTriple,List<Word> allDependences){
		
		for(int i=0;i<allDependences.size();i++){
			if(allDependences.get(i).getParent().equals(handleTriple.getTrajector().getId())&&allDependences.get(i).getRelate().equals("COO")){
				SpatialRelationshipTriple temp=new SpatialRelationshipTriple();
				temp.setDirectionNoun(handleTriple.getDirectionNoun());
				temp.setLandmark(handleTriple.getLandmark());
				temp.setTrajector(allDependences.get(i));
				resultOfSpatialRelationshipExtract.add(temp);
			}
		}
		return resultOfSpatialRelationshipExtract;
	}

	
}
