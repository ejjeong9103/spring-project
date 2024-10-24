const deleteButton = document.getElementById('delete-btn')

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        fetch(`/api/articles/${id}`, {
            method: 'DELETE' // http 메소드
            // json 형태를 보내줄수 있도록 어떠한 값을 보내주면 됨(나중에 배움)
        }).then(() => { // fetch 이후 실행되는 메소드 -> then()
            alert('삭제가 완료되었습니다');
            location.replace('/articles'); // location.href('')와 다름
        });
    });
}

const modifyButton = document.getElementById('modify-btn')
if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/api/articles/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            }, body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        }).then(() => {
            alert('수정이 완료되었습니다');
            location.replace(`/articles/${id}`);
        });
    })
}

const createButton = document.getElementById('create-btn');

if (createButton) {
    createButton.addEventListener('click', event => {
        fetch(`/api/articles`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            }),
        }).then(() => {
            alert('등록 완료되었습니다');
            location.replace("/articles");
        })
    })
}