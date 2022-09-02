package organizationsystem.util;

import org.springframework.beans.BeanUtils;

/**
 * DTO, Entity 등의 프로퍼티 값들을 복사한다.
 * 
 *
 */
public class CopyUtil {

	private static CopyUtil instance= null;
	
	private CopyUtil(){
		
	}
	
	public static synchronized void initialize(){
		instance= new CopyUtil();
	}
	
	public static CopyUtil instance(){
		if(instance == null){
			initialize();
		}
		
		return instance;
	}

	/**
	 * 객체의 프로퍼티 값들을 복사한다.
	 * @param source 복사할 원천 객체.null 일경우 복사하지 않는다.
	 * @param target 복사할 대상 객체.
	 */
	public static void copyProperties(Object source, Object target) {
		if(source != null) {
		BeanUtils.copyProperties(source, target);
		}
	}
	
	/**
	 * 특정 프로퍼티를 제외하고 객체의 프로퍼티 값들을 복사한다.
	 * @param source 복사할 원천 객체.null 일경우 복사하지 않는다.
	 * @param target 복사할 대상 객체.
	 * @param excludes 복사 제외할 프로퍼티 이름들.
	 */
	public static void copyProperties(Object source, Object target, String... excludes) {
		BeanUtils.copyProperties(source, target, excludes);
	}
}
