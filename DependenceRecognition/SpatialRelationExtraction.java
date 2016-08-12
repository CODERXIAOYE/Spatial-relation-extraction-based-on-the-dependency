package DependenceRecognition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import SpatialRelationship.Word;
import SpatialRelationship.SpatialRelationshipTriple;

/**
 * �ռ��ϵ��ȡ
 * @author Administrator
 *
 */
public class SpatialRelationExtraction {
	RecognizeHelper helper=new RecognizeHelper();//��������
	
	
	
	/**
	 * ��λ��ʶ��
	 * @param allWords һ�仰�����еĴ�
	 * @return ��λ���б�
	 */
	public List<Word> DirectionNounRecognize(List<Word> allWords){
			List<Word> directionNouns = new ArrayList<Word>();
			for(int i=0;i<allWords.size();i++){
				if(allWords.get(i).getPos().equals("nd")){//�ж��Ǵ����Ƿ�Ϊ��λ��
					directionNouns.add(allWords.get(i));
				}
			}
			if(directionNouns.size()==0){
				System.out.println("����û��ʶ�𵽷�λ��");
			}
			return directionNouns;
		}

	/**
	 * ���ʶ��
	 * @param allWords ���еĴ�
	 * @param directionNoun ��λ��
	 * @return ���
	 */
	public Word LandmarkRecognize(List<Word> allWords,Word directionNoun){
		List<Word> landmark=new ArrayList<Word>();
		//�������еĴʣ��ҵ����ڵ��Ƿ�λ�ʵ��Ǹ���
		for(int i=0;i<allWords.size();i++){
			if(allWords.get(i).getParent().equals(directionNoun.getId())){
				landmark.add(allWords.get(i));
			}
		}
		//��֤�������ϵΪATT
		for(int j=0;j<landmark.size();j++){
			if(!landmark.get(j).getRelate().equals("ATT")){
				landmark.remove(landmark.get(j));
			}
		}
		//ģ�Ϳ���
		while(true){//���ģ�Ϳ����������꣬��ôȷ����꣬�����ҵ��������ֱ��������󣬸���lamndmark���������жϡ�
			return landmark.get(0);
		}
	}

	/**
	 * ����ʶ��
	 * @param allWords ���д�
	 * @param directionNoun ��λ��
	 * @return
	 */

	public Word trajectorRecognize(List<Word> allWords,Word directionNoun){
		RecognizeHelper helper=new RecognizeHelper();
		//List<Word> resultOfTrajector=new ArrayList<Word>();
		Word resultOfTrajector=new Word();
		//�����λ�ʲ�����Ҫ�ķ�λ�ʣ������ϵΪATT
		if(directionNoun.getRelate().equals("ATT")){
			for(int i=0;i<allWords.size();i++){
				if(allWords.get(i).getId().equals(directionNoun.getParent())){
					resultOfTrajector=allWords.get(i);
				}
			}
			//��֤
			if(resultOfTrajector.getPos().contains("n")||resultOfTrajector.getPos().equals("r")){
				return resultOfTrajector;
			}
		}

		else{//���ڵ�������ϵΪHED���߸��ڵ�ĸ��ڵ��...���ڵ�������ϵΪHED
			resultOfTrajector=directionNoun;
			while(!helper.getParentWord(allWords, resultOfTrajector).getRelate().equals("HED")){
				resultOfTrajector=helper.getParentWord(allWords, resultOfTrajector);
			}
			for(int i=0;i<allWords.size();i++){
				if(allWords.get(i).getParent().equals(resultOfTrajector.getParent())&&(allWords.get(i).getPos().contains("n")
						||allWords.get(i).getPos().equals("r"))&&!allWords.get(i).getId().equals(resultOfTrajector.getId())){
						resultOfTrajector=allWords.get(i);
						return resultOfTrajector;
				}
	 		}
		}
		return resultOfTrajector;
	}

	
	/**
	 * ��ȡ�ռ��ϵ�ĳ�ȡ�����
	 * @return �ռ��ϵ��Ԫ���List
	 * @throws IOException 
	 */
	public List<SpatialRelationshipTriple> getSentenceSpatialTriple(String sentence) throws IOException{
		
		//����̾��ӣ���Ϊxml�ĸ�ʽ��֮���ȡ��Ϣ���õ�List<Word>�Ķ���
		CorpusPretreatment cp=new CorpusPretreatment();	
		List<Word> allDependences = cp.getWordProperty(sentence);
		List<SpatialRelationshipTriple> resultOfSpatialRelationshipExtract=new ArrayList<SpatialRelationshipTriple>();
		//��ȡ��λ��ʶ����
		List<Word> directionNouns=DirectionNounRecognize(allDependences);
		//һ�������λ�ʵĸ���������Ԫ��ĸ���,����һ������Ƕ����������Ԫ�飬����������
		for(int i=0;i<directionNouns.size();i++){
			SpatialRelationshipTriple temp=new SpatialRelationshipTriple();
			temp.setDirectionNoun(directionNouns.get(i));
			temp.setLandmark(LandmarkRecognize(allDependences, directionNouns.get(i)));
			temp.setTrajector(trajectorRecognize(allDependences, directionNouns.get(i)));
			resultOfSpatialRelationshipExtract.add(temp);
			//����������
			resultOfSpatialRelationshipExtract=helper.getAllTrajectorOfTriple(resultOfSpatialRelationshipExtract, temp, allDependences);
		}		
		return resultOfSpatialRelationshipExtract;
	}
}
