(testing Jenkins with webhook: #1)

# Build & Run instructions
1. clone this repo
2. $ mvn install  (compile + test)
3. $ mvn spring-boot:run
4. connect to rest api:
    - MJ: http://localhost:8080/restapi/artist/f27ec8db-af05-4f36-916e-3d57f91ecf5e
    - Nirvana: http://localhost:8080/restapi/artist/5b11f4ce-a62d-471e-81fc-a69a8278c7da

# About
The app uses Spring Boot which I chose mainly because I wanted to learn it more. I had only played around a bit with the GitHub API using Spring Boot and FeignClient before so figured this was a good opportunity to go a bit deeper.

The mentioned FeignClient was used for connecting to the three external apis. All three external api clients are wrapped in managers for testability and for encapsulating error handling. 

A response builder is created when a new request comes in and data added to it as it becomes available from the external apis. This builder also acts as the mapper from external api data format to our own response format.

### MusicBrainz
The app naturally fetches data from MusicBrainz first, since without that we can't go further. This manager is really the only one where I cared so much about error handling since failing here means we can't even partially fulfill our request.

### Cover Art Archive (CAA)
When it comes to cover art and artist description I swallow all exceptions and instead include "<no {cover art | description}>" in the response. I noticed in testing that not all albums have cover art in CAA so it makes sense to just return all that you can find and not pollute our response with any 3rd party error messages etc. It can of course be debated how strict we want our api to be but this is the path I went with now.

Since one artist can have many albums and CAA doesn't have a multi-fetch endpoint (and by far the worst response time of the three) we can't sit around and wait for all the albums to load one by one. The manager for CAA therefor immediately returns one Future representing all of the requests it will make for cover art (multi-threaded). That way we can carry on to get the artist description from Discogs in the meantime.   

### Discogs
I Had a super quick look at many of the "relations" and narrowed it down to three that I figured would have a bio of the artist and a good chance of having some kind of public api. Those were last.fm, Spotify and Google Play. Sadly they all required an account with them to get an access token to be able to use their apis. Last.fm even uses MBIDs so that would have been neat. However, I felt I had to fallback on Discogs. Not much more to say about Discogs. Straight forward fetch on id. Response time is quick so will always return before CAA is finished so no need to get fancy. You can make note of how this manager is designed to be easily replacable by for ex a SpotifyDescriptionManager etc. 

App then blocks on the CAA Future.get to wait for all cover art to come back before doing responseBuilder.build and returning the json.  


# Final words
I did leave out any caching of data. Reasoning being that it's easy to add and is not gonna show you anything more about me. I almost forgot to even mention it tho which would have been pretty bad. Also, you probably won't be able to actually load test the api because of rate limits in the 3rd party apis ;) Caching is of course key to handling heavy load in any app but particularily in one like this where you can have seconds of response time from downstream apis. 

I had some struggle with the error handling and Spring. I wanted my api to return json format even when something goes wrong but I couldn't get just throwing a ResponseStatusException (which you can see MusicBrainsException is) to work so had to resort to implementing a @ControllerAdvice etc. This fixes it for MusicBrainzExceptions which should be the vast majority but if you for ex call the api without a MBID you get the Whitelabel error page from Spring.

I wish I had added a few more tests but at least it's got the minimum required.

Probably the most interesting classes to look at are RestApiController, CoverArtManagerImpl and ResponseJsonBuilder.
