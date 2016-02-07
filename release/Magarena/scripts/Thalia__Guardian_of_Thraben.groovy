[
    new MagicStatic(MagicLayer.CostIncrease) {
        @Override
        public MagicManaCost increaseCost(final MagicPermanent source, final MagicCard card, final MagicManaCost cost) {
            if (card.isCreature() == false) {
                return cost.increase(1);
            } else {
                return cost;
            }
        }
    }
]
