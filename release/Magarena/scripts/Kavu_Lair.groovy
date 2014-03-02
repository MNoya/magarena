[
    new MagicWhenOtherComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent otherPermanent) {
            return (otherPermanent.isCreature() && otherPermanent.getPower() >= 4 ) ?
                new MagicEvent(
                    otherPermanent,
                    this,
                    "Whenever a creature with power 4 or greater enters the battlefield, its controller draws a card."
                ):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
			game.doAction(new MagicDrawAction(event.getPlayer()));
        }
    }
]
