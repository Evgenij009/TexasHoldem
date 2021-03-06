package com.epam.poker.model.database;

import com.epam.poker.model.Entity;

import java.math.BigDecimal;

public class ProfilePlayer implements Entity {
    private long userId;
    private BigDecimal bestPrize;
    private String award;
    private String photo;
    private String aboutYourselt;
    private BigDecimal lostMoney;
    private BigDecimal winMoney;

    public ProfilePlayer(long userId, BigDecimal bestPrize, String award, String photo,
                         String aboutYourselt, BigDecimal lostMoney, BigDecimal winMoney) {
        this.userId = userId;
        this.bestPrize = bestPrize;
        this.award = award;
        this.photo = photo;
        this.aboutYourselt = aboutYourselt;
        this.lostMoney = lostMoney;
        this.winMoney = winMoney;
    }

    private ProfilePlayer() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getLostMoney() {
        return lostMoney;
    }

    public void setLostMoney(BigDecimal lostMoney) {
        this.lostMoney = lostMoney;
    }

    public BigDecimal getWinMoney() {
        return winMoney;
    }

    public void setWinMoney(BigDecimal winMoney) {
        this.winMoney = winMoney;
    }

    public BigDecimal getBestPrize() {
        return bestPrize;
    }

    public void setBestPrize(BigDecimal bestPrize) {
        this.bestPrize = bestPrize;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAboutYourself() {
        return aboutYourselt;
    }

    public void setAboutYourselt(String aboutYourselt) {
        this.aboutYourselt = aboutYourselt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfilePlayer that = (ProfilePlayer) o;

        if (userId != that.userId) return false;
        if (bestPrize != null ? !bestPrize.equals(that.bestPrize) : that.bestPrize != null) return false;
        if (award != null ? !award.equals(that.award) : that.award != null) return false;
        if (photo != null ? !photo.equals(that.photo) : that.photo != null) return false;
        if (aboutYourselt != null ? !aboutYourselt.equals(that.aboutYourselt) : that.aboutYourselt != null)
            return false;
        if (lostMoney != null ? !lostMoney.equals(that.lostMoney) : that.lostMoney != null) return false;
        return winMoney != null ? winMoney.equals(that.winMoney) : that.winMoney == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (bestPrize != null ? bestPrize.hashCode() : 0);
        result = 31 * result + (award != null ? award.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (aboutYourselt != null ? aboutYourselt.hashCode() : 0);
        result = 31 * result + (lostMoney != null ? lostMoney.hashCode() : 0);
        result = 31 * result + (winMoney != null ? winMoney.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProfilePlayer{");
        sb.append("userId=").append(userId);
        sb.append(", bestPrize=").append(bestPrize);
        sb.append(", award='").append(award).append('\'');
        sb.append(", photo='").append(photo).append('\'');
        sb.append(", aboutYourselt='").append(aboutYourselt).append('\'');
        sb.append(", lostMoney=").append(lostMoney);
        sb.append(", winMoney=").append(winMoney);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static ProfilePlayerBuilder builder() {
        return new ProfilePlayerBuilder();
    }

    public static class ProfilePlayerBuilder {
        private final ProfilePlayer profilePlayer;

        public ProfilePlayerBuilder() {
            profilePlayer = new ProfilePlayer();
        }

        public ProfilePlayerBuilder setUserId(long userId) {
            profilePlayer.setUserId(userId);
            return this;
        }

        public ProfilePlayerBuilder setLostMoney(BigDecimal money) {
            profilePlayer.setLostMoney(money);
            return this;
        }

        public ProfilePlayerBuilder setWinMoney(BigDecimal money) {
            profilePlayer.setWinMoney(money);
            return this;
        }

        public ProfilePlayerBuilder setBestPrize(BigDecimal bestPrize) {
            profilePlayer.setBestPrize(bestPrize);
            return this;
        }

        public ProfilePlayerBuilder setAward(String award) {
            profilePlayer.setAward(award);
            return this;
        }

        public ProfilePlayerBuilder setPhoto(String photo) {
            profilePlayer.setPhoto(photo);
            return this;
        }

        public ProfilePlayerBuilder setAboutYourself(String lines) {
            profilePlayer.setAboutYourselt(lines);
            return this;
        }

        public ProfilePlayer createRatingPlayer() {
            return profilePlayer;
        }
    }
}
