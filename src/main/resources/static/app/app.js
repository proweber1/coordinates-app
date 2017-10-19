'use strict';

const protocol = location.protocol;
const slashes = protocol.concat("//");
const host = location.hostname.concat(':').concat(location.port);
const API_URL = slashes.concat(host).concat('/api');

function getNormalTimeUnitRepresent(timeUnit) {
    return timeUnit < 10 ? '0' + timeUnit : timeUnit;
}

new Vue({
    el: "#app",
    data: {
        /*
         This is search form model
         */
        form: {
            username: null,
            lat: null,
            lng: null
        },
        isSubmitButtonLocked: false,
        availableShops: []
    },
    methods: {
        isValidForm: function () {
            return this.form.username &&
                (this.form.lat && this.form.lat >= -180 && this.form.lat <= 180) &&
                (this.form.lng && this.form.lng >= -90 && this.form.lng <= 90);
        },

        /**
         * This method using for send http request to backend to
         * search shops what beside to user
         */
        searchShops: function () {
            if (!this.isValidForm()) return;

            const that = this;
            this.isSubmitButtonLocked = true;

            axios.get(API_URL + "/shops", {params: this.form})
                .then(function (response) {
                    that.availableShops = response.data;
                }).catch(function (error) {
                alert('Server error, please try later');
            }).then(function () {
                that.isSubmitButtonLocked = false;
            });
        },

        /**
         * This method convert work time of shop to human string
         *
         * @param shop Shop object
         * @returns {*}
         */
        workTime: function (shop) {
            if (!shop) return '(not available)';

            return shop.openTime && shop.closeTime
                ? 'from ' + this.$options.filters.time(shop.openTime) + ' to ' + this.$options.filters.time(shop.closeTime)
                : '24-hours';
        }
    },
    filters: {
        time: function (numberTimestamp) {
            const date = new Date(numberTimestamp);

            return getNormalTimeUnitRepresent(date.getHours()) + ':' +
                getNormalTimeUnitRepresent(date.getMinutes());
        },

        distance: function (value) {
            return value > 0 ? value : '< 1';
        }
    }
});