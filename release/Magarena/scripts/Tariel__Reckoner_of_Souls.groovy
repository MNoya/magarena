[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Removal),
        "Animate"
    ) {

        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [new MagicTapEvent(source)];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                MagicTargetChoice.TARGET_OPPONENT,
                this,
                "PN chooses a creature card at random from target opponent's\$ graveyard. PN puts that card onto the battlefield under PN's control."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPlayer(game, {
                final MagicCardList cards = new MagicCardList(game.filterCards(it,MagicTargetFilterFactory.CREATURE_CARD_FROM_GRAVEYARD));
                for (final MagicCard card : cards.getRandomCards(1)) {
                    game.doAction(new MagicReanimateAction(
                        card,
                        event.getPlayer()
                    ));
                }
            });
        }
    }
]
