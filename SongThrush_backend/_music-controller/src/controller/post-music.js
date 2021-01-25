module.exports = function makePostMusic({addMusic}){
   
    return async function postMusic(httpRequest){
        
        const info = httpRequest.body;

        try {
            const posted = await addMusic(info);
        
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