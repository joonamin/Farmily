

## 필독

* 일관된 코드 스타일과 테스트 무결성을 위해, Git pre-hook 을 사용합니다.
* `${projectRoot}/scripts/pre-commit` 파일을 `${gitRoot}/.git/hooks/pre-commit ` 경로로 복사합니다.
* `${gitRoot}/.git/hooks/pre-commit` 가 실행가능하도록 권한을 설정합니다. (set executable)
* 적용이 잘 되었는지 확인해봅시다.
	* 아래 예시를 참고합니다.
	* <img src="https://raw.githubusercontent.com/joonamin/UpicImageRepo/master/uPic/Screenshot%202024-01-17%20at%209.43.35%E2%80%AFPM.png" alt="Screenshot 2024-01-17 at 9.43.35 PM" style="zoom:50%;" />
	* githook이 잘 적용되었는지 확인하기 위해서, 무조건 실패하는 테스트 코드를 작성하여 commit 해봅시다.
	* 실패할 경우, commit이 취소되는 것을 확인합니다.



