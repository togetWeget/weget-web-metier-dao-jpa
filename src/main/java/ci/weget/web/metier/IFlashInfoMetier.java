package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.FlashInfo;

public interface IFlashInfoMetier extends Imetier<FlashInfo, Long> {
List<FlashInfo>	findAllFlashInfoParIdSousBlock(Long id);
}
