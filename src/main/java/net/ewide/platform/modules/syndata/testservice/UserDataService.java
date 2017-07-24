package net.ewide.platform.modules.syndata.testservice;

import net.ewide.platform.modules.syndata.event.AddUserData1Event;
import net.ewide.platform.modules.sys.entity.User;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDataService implements ApplicationEventPublisherAware{

	
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public void addData(){
		User user = new User();
		user.setEmail("xuanywn@163.com");
		AddUserData1Event event = new AddUserData1Event(user);
		publisher.publishEvent(event);
	/*	try {
			publisher.publishEvent(event);
		} catch (Exception e) {
			System.out.println("异常事件捕捉！");
			e.printStackTrace();
		}*/
	}

	@Override
	public void setApplicationEventPublisher(
			ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
		
	}
	
	
}
