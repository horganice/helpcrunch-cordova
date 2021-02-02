var helpcrunch = {
    initialize: function (organisationId, appId, secretId, success, error) {
        cordova.exec(success, error, 'HelpcrunchPlugin', 'initialize', [organisationId, appId, secretId]);
    },
    showChatScreen: function (success, error) {
        cordova.exec(success, error, 'HelpcrunchPlugin', 'showChatScreen', []);
    },
    getUnreadChatsCount: function (success, error) {
        cordova.exec(success, error, 'HelpcrunchPlugin', 'getUnreadChatsCount', []);
    }
}

module.exports = helpcrunch;