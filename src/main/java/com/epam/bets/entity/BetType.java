package com.epam.bets.entity;

import java.math.BigDecimal;

public enum BetType {
    FW {
        @Override
        public boolean isWinningBet(BigDecimal firstTeamScore, BigDecimal secondTeamScore, BigDecimal total) {
            return (firstTeamScore.compareTo(secondTeamScore) == 1);

        }
    },
    SW {
        @Override
        public boolean isWinningBet(BigDecimal firstTeamScore, BigDecimal secondTeamScore, BigDecimal total) {
            return (secondTeamScore.compareTo(firstTeamScore) == 1);
        }
    },

    X {
        @Override
        public boolean isWinningBet(BigDecimal firstTeamScore, BigDecimal secondTeamScore, BigDecimal total) {
            return (firstTeamScore.compareTo(secondTeamScore) == 0);
        }
    },
    FWX {
        @Override
        public boolean isWinningBet(BigDecimal firstTeamScore, BigDecimal secondTeamScore, BigDecimal total) {
            return (firstTeamScore.compareTo(secondTeamScore) == 1 || firstTeamScore.compareTo(secondTeamScore) == 0);
        }
    },
    XSW {
        @Override
        public boolean isWinningBet(BigDecimal firstTeamScore, BigDecimal secondTeamScore, BigDecimal total) {
            return (secondTeamScore.compareTo(firstTeamScore) == 1 || firstTeamScore.compareTo(secondTeamScore) == 0);
        }
    },
    FS {
        @Override
        public boolean isWinningBet(BigDecimal firstTeamScore, BigDecimal secondTeamScore, BigDecimal total) {
            return (firstTeamScore.compareTo(secondTeamScore) == 1 || secondTeamScore.compareTo(firstTeamScore) == 1);
        }
    },
    TM {
        @Override
        public boolean isWinningBet(BigDecimal firstTeamScore, BigDecimal secondTeamScore, BigDecimal total) {
            return (firstTeamScore.add(secondTeamScore).compareTo(total) == 1);
        }
    },
    TL {
        @Override
        public boolean isWinningBet(BigDecimal firstTeamScore, BigDecimal secondTeamScore, BigDecimal total) {
            return (firstTeamScore.add(secondTeamScore).compareTo(total) == -1);
        }
    };


    public abstract boolean isWinningBet(BigDecimal firstTeamScore, BigDecimal secondTeamScore, BigDecimal total);


}
