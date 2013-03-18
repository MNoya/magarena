[
    new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(
            final MagicGame game,
            final MagicPermanent permanent,
            final MagicPlayer player) {   
            if (permanent.isKicked()) {
                game.doAction(new MagicChangeCountersAction(
                        permanent,
                        MagicCounterType.PlusOne,
                        2,
                        true));
                game.doAction(new MagicSetAbilityAction(permanent,MagicAbility.FirstStrike,MagicStatic.Forever));
            }
            return MagicEvent.NONE;
        }
    }
]
