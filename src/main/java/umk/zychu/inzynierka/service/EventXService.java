package umk.zychu.inzynierka.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import umk.zychu.inzynierka.model.EventX;

import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXEvent;

public interface EventXService {

	Collection<EventX> getCustomEvents();
}
