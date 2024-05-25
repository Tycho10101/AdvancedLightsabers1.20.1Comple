package com.stelmods.lightsabers.client.render.hilt;

import com.stelmods.lightsabers.client.model.lightsaber.ModelBodyDroideka;
import com.stelmods.lightsabers.client.model.lightsaber.ModelBodyFulcrum;
import com.stelmods.lightsabers.client.model.lightsaber.ModelBodyFury;
import com.stelmods.lightsabers.client.model.lightsaber.ModelBodyGraflex;
import com.stelmods.lightsabers.client.model.lightsaber.ModelBodyImperial;
import com.stelmods.lightsabers.client.model.lightsaber.ModelBodyJuggernaut;
import com.stelmods.lightsabers.client.model.lightsaber.ModelBodyKnighted;
import com.stelmods.lightsabers.client.model.lightsaber.ModelBodyMandalorian;
import com.stelmods.lightsabers.client.model.lightsaber.ModelBodyMauler;
import com.stelmods.lightsabers.client.model.lightsaber.ModelBodyMechanical;
import com.stelmods.lightsabers.client.model.lightsaber.ModelBodyProdigalSon;
import com.stelmods.lightsabers.client.model.lightsaber.ModelBodyRebel;
import com.stelmods.lightsabers.client.model.lightsaber.ModelBodyReborn;
import com.stelmods.lightsabers.client.model.lightsaber.ModelBodyRedeemer;
import com.stelmods.lightsabers.client.model.lightsaber.ModelBodyVaid;
import com.stelmods.lightsabers.client.model.lightsaber.ModelEmitterDroideka;
import com.stelmods.lightsabers.client.model.lightsaber.ModelEmitterFulcrum;
import com.stelmods.lightsabers.client.model.lightsaber.ModelEmitterFury;
import com.stelmods.lightsabers.client.model.lightsaber.ModelEmitterGraflex;
import com.stelmods.lightsabers.client.model.lightsaber.ModelEmitterImperial;
import com.stelmods.lightsabers.client.model.lightsaber.ModelEmitterJuggernaut;
import com.stelmods.lightsabers.client.model.lightsaber.ModelEmitterKnighted;
import com.stelmods.lightsabers.client.model.lightsaber.ModelEmitterMandalorian;
import com.stelmods.lightsabers.client.model.lightsaber.ModelEmitterMauler;
import com.stelmods.lightsabers.client.model.lightsaber.ModelEmitterMechanical;
import com.stelmods.lightsabers.client.model.lightsaber.ModelEmitterProdigalSon;
import com.stelmods.lightsabers.client.model.lightsaber.ModelEmitterRebel;
import com.stelmods.lightsabers.client.model.lightsaber.ModelEmitterReborn;
import com.stelmods.lightsabers.client.model.lightsaber.ModelEmitterRedeemer;
import com.stelmods.lightsabers.client.model.lightsaber.ModelEmitterVaid;
import com.stelmods.lightsabers.client.model.lightsaber.ModelPommelDroideka;
import com.stelmods.lightsabers.client.model.lightsaber.ModelPommelFulcrum;
import com.stelmods.lightsabers.client.model.lightsaber.ModelPommelFury;
import com.stelmods.lightsabers.client.model.lightsaber.ModelPommelGraflex;
import com.stelmods.lightsabers.client.model.lightsaber.ModelPommelImperial;
import com.stelmods.lightsabers.client.model.lightsaber.ModelPommelJuggernaut;
import com.stelmods.lightsabers.client.model.lightsaber.ModelPommelKnighted;
import com.stelmods.lightsabers.client.model.lightsaber.ModelPommelMandalorian;
import com.stelmods.lightsabers.client.model.lightsaber.ModelPommelMauler;
import com.stelmods.lightsabers.client.model.lightsaber.ModelPommelMechanical;
import com.stelmods.lightsabers.client.model.lightsaber.ModelPommelProdigalSon;
import com.stelmods.lightsabers.client.model.lightsaber.ModelPommelRebel;
import com.stelmods.lightsabers.client.model.lightsaber.ModelPommelReborn;
import com.stelmods.lightsabers.client.model.lightsaber.ModelPommelRedeemer;
import com.stelmods.lightsabers.client.model.lightsaber.ModelPommelVaid;
import com.stelmods.lightsabers.client.model.lightsaber.ModelSwitchSectionDroideka;
import com.stelmods.lightsabers.client.model.lightsaber.ModelSwitchSectionFulcrum;
import com.stelmods.lightsabers.client.model.lightsaber.ModelSwitchSectionFury;
import com.stelmods.lightsabers.client.model.lightsaber.ModelSwitchSectionGraflex;
import com.stelmods.lightsabers.client.model.lightsaber.ModelSwitchSectionImperial;
import com.stelmods.lightsabers.client.model.lightsaber.ModelSwitchSectionJuggernaut;
import com.stelmods.lightsabers.client.model.lightsaber.ModelSwitchSectionKnighted;
import com.stelmods.lightsabers.client.model.lightsaber.ModelSwitchSectionMandalorian;
import com.stelmods.lightsabers.client.model.lightsaber.ModelSwitchSectionMauler;
import com.stelmods.lightsabers.client.model.lightsaber.ModelSwitchSectionMechanical;
import com.stelmods.lightsabers.client.model.lightsaber.ModelSwitchSectionProdigalSon;
import com.stelmods.lightsabers.client.model.lightsaber.ModelSwitchSectionRebel;
import com.stelmods.lightsabers.client.model.lightsaber.ModelSwitchSectionReborn;
import com.stelmods.lightsabers.client.model.lightsaber.ModelSwitchSectionRedeemer;
import com.stelmods.lightsabers.client.model.lightsaber.ModelSwitchSectionVaid;
import com.stelmods.lightsabers.common.hilt.HiltManager;

public class HiltRendererManager
{
    public static void register()
    {
        HiltRenderer.register(HiltManager.GRAFLEX, new HiltRendererBase(new ModelEmitterGraflex(), new ModelSwitchSectionGraflex(), new ModelBodyGraflex(), new ModelPommelGraflex()));
        HiltRenderer.register(HiltManager.REDEEMER, new HiltRendererBase(new ModelEmitterRedeemer(), new ModelSwitchSectionRedeemer(), new ModelBodyRedeemer(), new ModelPommelRedeemer()));
        HiltRenderer.register(HiltManager.MAULER, new HiltRendererBase(new ModelEmitterMauler(), new ModelSwitchSectionMauler(), new ModelBodyMauler(), new ModelPommelMauler()));
        HiltRenderer.register(HiltManager.PRODIGAL_SON, new HiltRendererBase(new ModelEmitterProdigalSon(), new ModelSwitchSectionProdigalSon(), new ModelBodyProdigalSon(), new ModelPommelProdigalSon()));
        HiltRenderer.register(HiltManager.KNIGHTED, new HiltRendererBase(new ModelEmitterKnighted(), new ModelSwitchSectionKnighted(), new ModelBodyKnighted(), new ModelPommelKnighted()));
        HiltRenderer.register(HiltManager.VAID_ANCIENT, new HiltRendererBase(new ModelEmitterVaid(), new ModelSwitchSectionVaid(), new ModelBodyVaid(), new ModelPommelVaid()));
        HiltRenderer.register(HiltManager.VAID_MODERN, new HiltRendererBase(new ModelEmitterVaid(), new ModelSwitchSectionVaid(), new ModelBodyVaid(), new ModelPommelVaid()));
        HiltRenderer.register(HiltManager.DROIDEKA, new HiltRendererBase(new ModelEmitterDroideka(), new ModelSwitchSectionDroideka(), new ModelBodyDroideka(), new ModelPommelDroideka()));
        HiltRenderer.register(HiltManager.FULCRUM, new HiltRendererBase(new ModelEmitterFulcrum(), new ModelSwitchSectionFulcrum(), new ModelBodyFulcrum(), new ModelPommelFulcrum()));
        HiltRenderer.register(HiltManager.JUGGERNAUT, new HiltRendererBase(new ModelEmitterJuggernaut(), new ModelSwitchSectionJuggernaut(), new ModelBodyJuggernaut(), new ModelPommelJuggernaut()));
        HiltRenderer.register(HiltManager.MECHANICAL, new HiltRendererBase(new ModelEmitterMechanical(), new ModelSwitchSectionMechanical(), new ModelBodyMechanical(), new ModelPommelMechanical()));
        HiltRenderer.register(HiltManager.MANDALORIAN, new HiltRendererBase(new ModelEmitterMandalorian(), new ModelSwitchSectionMandalorian(), new ModelBodyMandalorian(), new ModelPommelMandalorian()));
        HiltRenderer.register(HiltManager.FURY, new HiltRendererBase(new ModelEmitterFury(), new ModelSwitchSectionFury(), new ModelBodyFury(), new ModelPommelFury()));
        HiltRenderer.register(HiltManager.REBEL, new HiltRendererBase(new ModelEmitterRebel(), new ModelSwitchSectionRebel(), new ModelBodyRebel(), new ModelPommelRebel()));
        HiltRenderer.register(HiltManager.IMPERIAL, new HiltRendererBase(new ModelEmitterImperial(), new ModelSwitchSectionImperial(), new ModelBodyImperial(), new ModelPommelImperial()));
        HiltRenderer.register(HiltManager.REBORN, new HiltRendererOneTwelve(new ModelEmitterReborn(), new ModelSwitchSectionReborn(), new ModelBodyReborn(), new ModelPommelReborn()));
    }
}
