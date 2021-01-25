module.exports = function makeGetRooms({readRoom}){
   
    return async function getRooms(httpRequest){
        
        const info = httpRequest.body;

        try {
            const readed = await readRoom(info);
        
            return {
                headers: {
                    'Content-Type': 'application/json',
                },
                statusCode: 200,
                body: readed
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