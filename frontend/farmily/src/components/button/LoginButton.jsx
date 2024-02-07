export default function LoginButton(site) {
    const baseUrl = import.meta.env.VITE_API_URL
    return (
        <button className="h-2/3">
            <a
                href={`${baseUrl}/oauth2/authorization/${site.url}`}
            >
                <img className="h-2/3 object-cover" src={site.image} alt="logo"/>
                <p>{site.name} 로그인</p>
            </a>
        </button>
    );
}
