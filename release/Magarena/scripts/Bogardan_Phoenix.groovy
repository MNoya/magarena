[
    new MagicWhenDiesTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicPermanent died) {
            return permanent.getCounters(MagicCounterType.Death) == 0 ?
                new MagicEvent(
                    permanent,
                    this,
                    "Return SN to the battlefield under your control and put a death counter on it."
                ):
                MagicEvent.NONE;
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicCard card = event.getPermanent().getCard();
            if (card.isInGraveyard()) {
                game.doAction(new ReanimateAction(card,event.getPlayer()));
                game.doAction(new ChangeCountersAction(event.getPermanent(),MagicCounterType.Death,1));
            }    
        }
    },
    new MagicWhenDiesTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicPermanent died) {
            return permanent.getCounters(MagicCounterType.Death) > 0 ?
                new MagicEvent(
                    permanent,
                    this,
                    "Exile SN."
                ):
                MagicEvent.NONE;
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicCard card = event.getPermanent().getCard();
            if (card.isInGraveyard()) {
                game.doAction(new RemoveCardAction(card,MagicLocationType.Graveyard));
                game.doAction(new MoveCardAction(card,MagicLocationType.Graveyard,MagicLocationType.Exile));
            }    
        }
    }
]
