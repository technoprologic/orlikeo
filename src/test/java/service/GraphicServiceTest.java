package service;

import org.junit.Test;


/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RepositoriesTestContextConfiguration.class, ServicesTestContextConfig.class},
        loader=AnnotationConfigContextLoader.class)
@Profile("dev")*/
public class GraphicServiceTest {

    /*@Autowired
    private GraphicDaoRepository graphicDAO;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserEventService userEventService;
    @Autowired
    private UserEventDecisionService userEventDecisionService;
    @Autowired
    private EventStateService eventStateService;
    @Autowired
    private UserEventRoleService userEventRoleService;
    @Autowired
    private EventToApproveService eventToApproveService;
    @Autowired
    private UserService userService;*/
/*

    private Graphic graphic;

    @Before
    public void before(){
        // Expected objects
        User user = new User.Builder("animator@email.pl", "password").build();
        Date start = new Date(2018, 1, 1, 12, 0);
        Date end = new Date(2018, 1, 1, 12, 0);
        DHXEvent dhxEvent = new DHXEvent(100, start, end, "DHXEvent");
        Orlik orlik = new Orlik.Builder("Street", "City", user)
                .build();
        graphic = new Graphic(dhxEvent, orlik);
        graphic.setAvailable(true);

        Set<Event> events = new HashSet<>();

        IntStream.range(1, 5)
                .forEach( i -> {
                    Event ev = new Event.Builder(user, eventStateService.findOne(EventState.READY_TO_ACCEPT))
                            .graphic(graphic)
                            .playersLimit(12).build();
                    ev.setId(i);
                    events.add(ev);
                });

        graphic.setEvent(events);
    }
*/

    @Test
    public void shouldDeleteGraphicAndReduceEventsToBasket(){


        // Mockito expectations
/*
        when(accountDAO.save(any(Account.class))).thenReturn(persistedAccount);
        doNothing().when(notificationService).notifyOfNewAccount(accountId);
*/

        // Execute the method being tested
/*
        Account newAccount = accountService.createNewAccount(name);
*/

        // Validation
    }

}
