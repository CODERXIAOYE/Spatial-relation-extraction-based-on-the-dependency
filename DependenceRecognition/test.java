package DependenceRecognition;

import java.io.IOException;
import java.util.List;

import SpatialRelationship.SpatialRelationshipTriple;

public class test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		SpatialRelationExtraction sre=new SpatialRelationExtraction();
		List<SpatialRelationshipTriple> result=(List<SpatialRelationshipTriple>)sre.getSentenceSpatialTriple("С���Ͱ°��������Ӻ�������ϵ��㡣");
		System.out.println("�ռ���Ԫ�����Ϊ��"+result.size());

		for(int i=0;i<result.size();i++){
			try{
				System.out.println("����: "+result.get(i).getTrajector().getCont()+" ��꣺"+result.get(i).getLandmark().getCont()+" ��λ�ʣ� "+result.get(i).getDirectionNoun().getCont());
			}
			catch(NullPointerException e){
				System.out.println("��Ԫ����ȡʧ��");
			}
		}
		
		
	}

}
