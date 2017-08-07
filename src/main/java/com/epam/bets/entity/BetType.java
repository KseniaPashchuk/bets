package com.epam.bets.entity;

public enum BetType {
    FW {
        @Override
        public boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total) {
           return (firstTeamScore > secondTeamScore);

        }
    },
    SW {
        @Override
        public boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total) {
           return (secondTeamScore > firstTeamScore);
        }
    },

    X {
        @Override
        public boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total) {
            return (firstTeamScore == secondTeamScore);
        }
    },
    FWX {
        @Override
        public boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total) {
            return (firstTeamScore >= secondTeamScore);
        }
    },
    XSW {
        @Override
        public boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total) {
           return (secondTeamScore >= firstTeamScore);
        }
    },
    FS {
        @Override
        public boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total) {
            return (secondTeamScore > firstTeamScore || firstTeamScore > secondTeamScore);
        }
    },
    TM {
        @Override
        public boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total) {
            return (firstTeamScore + secondTeamScore > total);
        }
    },
    TL {
        @Override//TODO
        public boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total) {
            return (firstTeamScore + secondTeamScore < total);
        }
    };


    public abstract boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total);

}
