package umk.zychu.inzynierka.service;

import umk.zychu.inzynierka.model.Graphic;

public interface GraphicService {
	Graphic findOne(Integer id);
	Graphic save(Graphic graphic);
	void delete(Graphic entity);
}
