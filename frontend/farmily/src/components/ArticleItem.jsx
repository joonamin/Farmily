export default function ArticleItem(article){
  return (
    // 게시글 디테일 페이지 만든 후 Link 달아주기
    <tr className="border-b-2">
      <td>{ article.title }</td>
      <td>{ article.nickname }</td>
      <td>{ article.date }</td>
    </tr>
  )
}