package umk.zychu.inzynierka.controller.handlers;

import java.util.function.Function;

/**
 * Created by emag on 01.06.17.
 */
@FunctionalInterface
public interface CommandHandler<C extends Command, R> extends Function<C, R>{

}
