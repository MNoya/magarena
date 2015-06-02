[
    new MagicWhenBlocksOrBecomesBlockedTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPermanent blocker) {
            final MagicPermanent target = permanent == blocker ? blocker.getBlockedCreature() : blocker;
            return new MagicEvent(
                    permanent,
                    target,
                    this,
                    "Destroy RN at end of combat."
                );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processRefPermanent(game, {
                game.doAction(new AddTurnTriggerAction(
                    it,
                    MagicAtEndOfCombatTrigger.Destroy
                ))
            });
        }
    }
]
