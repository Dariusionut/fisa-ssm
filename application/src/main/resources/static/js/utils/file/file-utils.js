
export const uploadFile = () => {
    return new Promise((resolve, reject) => {
        const input = document.createElement('input');
        input.type = 'file'
        input.click();
        input.addEventListener('change', e => {
            const {target} = e;
            const fileList = target.files;

            let files = [];

            if (fileList){
                files = Array.from(fileList);
            }

            resolve(files);
            
        })
    })
}
