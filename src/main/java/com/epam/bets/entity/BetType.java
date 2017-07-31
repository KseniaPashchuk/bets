package com.epam.bets.entity;

public enum BetType {
    FW {
        @Override
        public boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total) {
            if (firstTeamScore > secondTeamScore) {
                return true;
            }
            return false;
        }
    },
    SW {
        @Override
        public boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total) {
            if (secondTeamScore > firstTeamScore) {
                return true;
            }
            return false;
        }
    },

    X {
        @Override
        public boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total) {
            if (firstTeamScore == secondTeamScore) {
                return true;
            }
            return false;
        }
    },
    FWX {
        @Override
        public boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total) {
            if (firstTeamScore >= secondTeamScore) {
                return true;
            }
            return false;
        }
    },
    XSW {
        @Override
        public boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total) {
            if (secondTeamScore >= firstTeamScore) {
                return true;
            }
            return false;
        }
    },
    FS {
        @Override
        public boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total) {
            if (secondTeamScore > firstTeamScore || firstTeamScore > secondTeamScore) {
                return true;
            }
            return false;
        }
    },
    TM {
        @Override
        public boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total) {
            if (firstTeamScore + secondTeamScore > total) {
                return true;
            }
            return false;
        }
    },
    TL {
        @Override
        public boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total) {
            if (firstTeamScore + secondTeamScore < total) {
                return true;
            }
            return false;
        }
    };


    public abstract boolean isWinningBet(int firstTeamScore, int secondTeamScore, int total);

}
