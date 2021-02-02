var helpcrunch = {
    initialize: function (organisationId, appId, secretId, success, error) {
        cordova.exec(success, error, 'Helpcrunch', 'initialize', [organisationId, appId, secretId]);
    },
    showChatScreen: function (success, error) {
        cordova.exec(success, error, 'Helpcrunch', 'showChatScreen', []);
    }
}

module.exports = helpcrunch;