package com.augylabs.mypackagelisting;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        library = true,
        injects = {MyActivity.class})
public class MainModule {

    @Provides
    @Singleton
    GitHub buildGitHubRestClient() {

        return new GitHub();
    }
}


