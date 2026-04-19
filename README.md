### Details about some tech decisions I made 

I wanted the code to be easy to read and easy to test.
and  make sure it is easy to extend without adding complexity to the code.

I used a layered approach, which is presentation, domain and data with MVVM because it gives clear responsibility boundaries.
The UI code stays in the UI files, business decisions stay in the use cases and the API and database details stay in the repository and data files.

### Key Files and Why They Exist

--MainActivity.kt--
    Only job is to serve as the app entry point and set the Compose content

--StackOverflowApplication.kt--
    Required for Hilt setup. I used it so that dependencies are created and managed consistently across the StackOverflowApplication.

--NetworkModule.kt--
    Place for Retrofit and OkHttp creation, here to avoid networking setup and to make dependency changes easier later on.

--StackOverflowApi.kt -- -- StackOverflowApiConstants.kt --
    Basically, separated the API contract from the constants,
    so that endpoint and query defaults are not hardcoded in multiple places. 
    This makes the code more efficient and easier to maintain.

--UserDto.kt, UsersResponse.kt, UserDtoMapper.kt --
    DTO models -> raw API data, mapper converts DTO -> domain model.
    Separated to avoid leaking the network structure into the rest of the StackOverflowApplication.

--UserRepository.kt,UserRepositoryImpl.kt--
    Used repository abstraction to hide where the data comes from.
    UI and domain layers do not need to know the details of Retrofit.

--FollowDao.kt,FollowEntity.kt,SoDatabase.kt--
    Room for follow state persistence, kept the storage small and focused on one responsibility.

--FollowRepository.kt,FollowRepositoryImpl.kt--
    The following and unfollowing logic is isolated from the UI and database,
    which makes the behavior easier to test.

--ObserveUsersWithFollowStateUseCase.kt--
    Combines the users with the local follow state
    put this merge logic in the domain layer so that the ViewModel stay simple.

--ToggleFollowUseCase.kt--
    Follow and unfollow decision is business logic not UI logic,
    keeps the behavior reusable and testable.

--UserListViewModel.kt,UserListUiState.kt--
    Handles the state transitions and async work.
    StateFlow for the screen state and SharedFlow for one-time events like transient error messages.

--UserListScreen.kt,UI components--
    UI is split into composable so that each part is easier to reason about and reuse.

--AppError.kt.AppResult.kt,AppErrorUiText.kt--
    Basically introduced typed error and result handling to avoid exception 
    handling and to keep the user-facing error mapping explicit.

I really try to not over-engineer all of this with multi-module setup since this is a simple assignment,
    I am aware that multi-modular is a practice today I focused on boundaries,
    naming consistency testable logic...

### HOW TO TEST
Clone the repository, sync the gradle and run the app, open it check if there is 20 top users from stackoverflow,
try to follow, unfollow, follow, close the app, open it again to see data persistence.