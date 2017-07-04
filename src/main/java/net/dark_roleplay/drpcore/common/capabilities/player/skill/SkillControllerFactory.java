package net.dark_roleplay.drpcore.common.capabilities.player.skill;

import java.util.concurrent.Callable;

public class SkillControllerFactory  implements Callable<ISkillController> {

	  @Override
	  public ISkillController call() throws Exception {
	    return new SkillControllerDefault();
	  }
}