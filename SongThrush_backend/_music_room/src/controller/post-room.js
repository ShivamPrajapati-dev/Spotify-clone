module.exports = function makePostRoom({createRoom}){
   
    return async function postRoom(httpRequest){
        
        const info = httpRequest.body;

        try {
            const posted = await createRoom(info);
        
            return {
                headers: {
                    'Content-Type': 'application/json',
                },
                statusCode: 201,
                body: posted
            }            
        
        } catch (e) {
        
            return {
                headers: {
                    'Content-Type': 'application/json',
                },
                statusCode: 400,
                body: e.message
            }
        
        }

    }
}