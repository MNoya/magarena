[
    new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicPayedCost payedCost) {
            return new MagicEvent(
                permanent,
                NEG_TARGET_CARD_FROM_ALL_GRAVEYARDS,
                MagicGraveyardTargetPicker.ExileOpp,
                this,
                "Put target card\$ from a graveyard " +
                "on the bottom of its owner's library."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetCard(game, {
                game.doAction(new RemoveCardAction(it,MagicLocationType.Graveyard));
                game.doAction(new MoveCardAction(
                    it,
                    MagicLocationType.Graveyard,
                    MagicLocationType.BottomOfOwnersLibrary
                ));
            });
        }
    }
]
